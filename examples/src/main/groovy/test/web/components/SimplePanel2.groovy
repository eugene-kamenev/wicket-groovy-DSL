package test.web.components

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.panel.Panel
import wicket.groovy.WicketDSL

class SimplePanel2 extends Panel {
    SimplePanel2(String id) {
        super(id)
        use(WicketDSL) {
            this + ['one', 'two', 'three'].listView('listView') { ListItem item ->
                item.label('label', [model: item.model])
            }
        }
    }
}
