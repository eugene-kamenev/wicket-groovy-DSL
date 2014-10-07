package test.web

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors

@CompileStatic
@InheritConstructors
class ListViewPage extends TemplatePage {
    ListViewPage() {
        listView('listView', ['one', 'two', 'tree', 'four']) {
            label('label').model(it.model)
        }
    }
}
