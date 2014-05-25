package test.web

import org.apache.wicket.markup.html.list.ListItem
import wicket.groovy.GroovyDSL

class ListViewPage extends TemplatePage {
    ListViewPage() {
        use(GroovyDSL) {
            this << ['one', 'two', 'three', 'four'].listView('listView') { ListItem item ->
                item.label 'label', item.model
            }
        }
    }
}
