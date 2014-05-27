package test.web

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.PropertyModel
import test.domain.Person
import wicket.groovy.WicketDSL

class HomePage extends TemplatePage {
    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL) {
            def listView = Person.findAll([fetch:[department:'eager']]).listView('personList')
            { ListItem<Person> item ->
                item.label 'personName', item.model
                item.label 'department', new PropertyModel(item.model, 'department.title')
            }
            bookmarkLink('addPerson', AddPersonPage)
            this << listView
        }
    }
}
