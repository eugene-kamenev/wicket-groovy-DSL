package test.web

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
            def form = form('personForm', [model: new CompoundPropertyModel(this), submit: {
                Person.withTransaction {
                    if (!person.save(insert: true)) {
                        person.errors.allErrors.each { ObjectError e ->
                            error("${e.objectName}.${e.defaultMessage}")
                        }
                    }
                }
            }])
            form.text('person.name')
            form.dropDown('person.department', [choices: departments, render: [id: 'id', value: 'title']])
            form + personListFragment()
            form + new FeedbackPanel('errors')
        }
    }

    public List<Department> getDepartments() {
        Department.findAll()
    }
}
