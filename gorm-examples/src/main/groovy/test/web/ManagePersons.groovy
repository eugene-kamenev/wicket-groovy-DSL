package test.web
import grails.gorm.DetachedCriteria
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.markup.html.panel.Fragment
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.Model
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import test.domain.Department
import test.domain.Person
import wicket.groovy.gorm.WicketGormDSL

class ManagePersons extends CreateEditViewPage {
    Fragment fragment

    ManagePersons() {
        view()
    }

    ManagePersons(PageParameters parameters) {
        def id
        if (parameters.getNamedKeys().contains('id')) {
            id = parameters.get('id').toLong()
            edit(id)
        } else {
            if (parameters.getNamedKeys().contains('create')) {
                edit(0L)
            } else if (parameters.getNamedKeys().contains('department')) {
                viewByDepartment(parameters.get('department').toLong())
            }
        }

    }


    @Override
    void view() {
        use(WicketGormDSL) {
            fragment = fragment('content', 'view') {
                def view = it.beanDataView('persons', [max     : { 5 },
                                                       criteria: { new DetachedCriteria(Person) },
                                                       params  : [fetch: ['department': 'eager']],
                                                       fields  : ['email', 'name']]) { Item<Person> item ->
                    item.label 'id'
                    item.bookmarkLink('departmentLink', ManageDepartments, [params: ['id': item.modelObject.id]]).label 'department.title'
                    item.ajaxLink 'delete', [model: item.model, click: { AjaxRequestTarget a, AjaxLink<Person> link ->
                        Person.withTransaction {
                            link.modelObject.delete(flush: true)
                        }
                        fragment.modelChanged()
                        a.add(fragment)
                    }]
                    item.bookmarkLink('edit', ManagePersons, [params: ['id': item.modelObject.id]])
                }
                it + new AjaxPagingNavigator('navigation', view)
                it.setOutputMarkupId(true)
                it.bookmarkLink('createNew', ManagePersons, [params: ['create': 'new']])
            }
        }

    }

    @Override
    void edit(Long id) {
        use(WicketGormDSL) {
            def model = id ? loadModel { Person.findById(id, [fetch: ['department': 'eager']]) }
                    : Model.of(new Person())
            fragment = fragment('content', 'edit') {
                it.entityForm 'person', [model : model,
                                         submit: { form -> println form.modelObject },
                                         fields: ['name', 'email', 'password', 'department']]
                it + new FeedbackPanel('errors')
            }
        }
    }

    void viewByDepartment(Long departmentId) {
        use(WicketGormDSL) {
            fragment = fragment 'content', 'viewByDepartment', {
                def model = loadModel { Department.findById(departmentId, [fetch: [persons: 'eager']])}
                it.label 'department', [model: new PropertyModel(model, 'title')]
                it.beanDataView 'persons', [max     : { 5 },
                                            criteria: {
                                                Person.where { department == model.object }
                                            }], { Item<Person> p ->
                    p.bookmarkLink('personLink', ManagePersons, [params: ['id': p.modelObject.id]]).label('name')
                }
                it.bookmarkLink 'departments', ManageDepartments
            }
        }
    }
}
