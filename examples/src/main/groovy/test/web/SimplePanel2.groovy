package test.web

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.panel.Panel
import wicket.groovy.GroovyDSL

class SimplePanel2 extends Panel {
    SimplePanel2(String id) {
        super(id)
        use(GroovyDSL) {
            this << ['one', 'two', 'three'].listView('listView') { ListItem item ->
                item.label('label', item.model)
            }
        }
    }
}
