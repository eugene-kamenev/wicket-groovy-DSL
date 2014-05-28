package test.web

import org.apache.wicket.markup.html.list.ListItem
import wicket.groovy.WicketDSL

class ListViewPage extends TemplatePage {
    ListViewPage() {
        use(WicketDSL) {
            this + ['one', 'two', 'three', 'four'].listView('listView') { ListItem item ->
                item.label 'label', item.model
            }
        }
    }
}
