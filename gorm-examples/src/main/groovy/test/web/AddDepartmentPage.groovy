package test.web

import org.apache.wicket.RestartResponseException
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.panel.Fragment
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.PropertyModel
import test.domain.Department
import test.domain.Person
import wicket.groovy.WicketDSL
import wicket.groovy.WicketFormDSL

class AddDepartmentPage extends TemplatePage {

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL, WicketFormDSL) {
            fragment('operation', 'department') {
                it + Department.findAll().listView('departments', [visible: { true }]) {
                    ListItem<Department> item ->
                        item.ajaxLink('link', [model: item.model, click: {
                            it.add page.get('operation') >> department(item.modelObject)
                        }]).label 'title', [model: new PropertyModel(item.model, 'title')]
                }
                it.ajaxLink('createNew', [click: {
                    it.add page.get('operation') >> department(new Department())
                }])
                it.setOutputMarkupId(true)
            }
        }
    }

    Fragment department(Department department) {
        def fragment = WicketDSL.fragment(null, 'operation', 'departmentAdd', [provider: this]) { Fragment fr ->
            use(WicketDSL, WicketFormDSL) {
                def persons = department.persons.collect { it }
                def form = fr.form 'form', [model : new CompoundPropertyModel(department),
                                            submit: { Form<Department> f ->
                                                Department.withTransaction {
                                                    def d = f.modelObject
                                                    d.persons*.department = d
                                                    persons.each { p ->
                                                        if (!d.persons.contains(p)) {
                                                            p.department = null
                                                            p.merge()
                                                        }
                                                    }
                                                    if (d.id) {
                                                        d.merge(flush: true)
                                                    } else {
                                                        d.save(insert: true)
                                                    }
                                                }
                                                throw new RestartResponseException(HomePage)
                                            }]
                form.text('title')
                form.checkChoices('persons', [choices: loadModel { Person.findAll() },
                                              render : [id: 'id', value: 'name']])
            }
            fr.outputMarkupId = true
        }
        fragment
    }
}
