package test.web

import groovy.transform.CompileStatic

@CompileStatic
class ListViewPage extends TemplatePage {
    ListViewPage() {
        listView('listView', ['one', 'two', 'tree', 'four']) {
            label('label').model(it.model)
        }
    }
}
