package test.web

import grails.gorm.DetachedCriteria
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.markup.html.panel.Fragment
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters
import test.domain.Department
import test.domain.Person
import wicket.groovy.gorm.WicketGormDSL

class ManageDepartments extends CreateEditViewPage {
    Fragment fragment

    ManageDepartments() {
        view()
    }

    ManageDepartments(PageParameters parameters) {
        super(parameters)
        def id = parameters.getNamedKeys().contains('id') ? parameters.get('id').toLong() : 0L
        edit(id)
    }

    @Override
    void view() {
        use(WicketGormDSL) {
            fragment = fragment('content', 'view') {
                def view = it.beanDataView('departments', [max     : { 5 },
                                                           criteria: { new DetachedCriteria(Department) },
                                                           fields  : ['title', 'enabled']]) { Item<Department> item ->
                    item.label 'id'
                    item.bookmarkLink('personLink', ManagePersons, [params: [department: item.modelObject.id]]).label('personCount', [model: loadModel {
                        Person.countByDepartment(item.modelObject)
                    }])
                    item.ajaxLink 'delete', [model: item.model, click: { AjaxRequestTarget a, AjaxLink<Department> link ->
                        Department.withTransaction {
                            link.modelObject.persons*.department = null
                            link.modelObject.delete(flush: true)
                        }
                        fragment.modelChanged()
                        a.add(fragment)
                    }]
                    item.bookmarkLink('edit', ManageDepartments, [params: ['id': item.modelObject.id]])
                }
                it + new AjaxPagingNavigator('navigation', view)
                it.setOutputMarkupId(true)
                it.bookmarkLink('createNew', ManageDepartments, [params: ['create': 'new']])
            }
        }
    }

    @Override
    void edit(Long id) {
        use(WicketGormDSL) {
            def model = id ? loadModel { Department.findById(id, [fetch: ['persons': 'eager']]) }
                    : Model.of(new Department())
            fragment = fragment('content', 'edit') {
                def persons = model.object.persons.collect { it } as List<Person>
                it.entityForm('department', [model : model,
                                             submit: { saveDepartment(it.modelObject, persons) },
                                             fields: ['title', 'enabled', 'persons']])
                it + new FeedbackPanel('errors')
            }
        }
    }

    static void saveDepartment(Department d, List<Person> persons) {
        Department.withTransaction {
            d.persons*.department = d
            persons.each {
                if (!d.persons.contains(it)) {
                    it.department = null
                    it.merge()
                }
            }
            if (d.id) {
                d.merge(flush: true)
            } else {
                d.save(insert: true)
            }
        }
    }

}
