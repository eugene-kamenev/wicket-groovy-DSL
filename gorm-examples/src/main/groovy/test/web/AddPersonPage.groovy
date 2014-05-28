package test.web
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.CompoundPropertyModel
import org.springframework.validation.ObjectError
import test.domain.Department
import test.domain.Person
import wicket.groovy.WicketDSL
import wicket.groovy.WicketFormDSL

class AddPersonPage extends TemplatePage {
    Person person = new Person()

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL, WicketFormDSL) {
            def form = form('personForm', new CompoundPropertyModel(this), [submit: {
                Person.withTransaction {
                    if (!person.save(insert: true)) {
                        person.errors.allErrors.each { ObjectError e ->
                            error("${e.objectName}.${e.defaultMessage}")
                        }
                    }
                }
            }])
            form.text('person.name')
            form.dropDown('person.department', departments, [id: 'id', value: 'title'])

            def personListModel = loadableModel() {
                Person.findAll([fetch: [department: 'eager']])
            }
            listView('personList', personListModel, null)
                    { ListItem<Person> item ->
                        item.defaultModel = new CompoundPropertyModel(item.model)
                        item.label 'name'
                        item.label 'department.title'
                    }
            form + new FeedbackPanel('errors')
        }
    }

    public List<Department> getDepartments() {
        Department.findAll()
    }
}
