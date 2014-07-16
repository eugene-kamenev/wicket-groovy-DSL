package test.web.components

import org.apache.wicket.markup.html.panel.Panel

class SimplePanel2 extends Panel {
    SimplePanel2(String id) {
        super(id)
        listView('listView', ['one', 'two', 'three']) {
            label('label').model()
        }
    }
}
