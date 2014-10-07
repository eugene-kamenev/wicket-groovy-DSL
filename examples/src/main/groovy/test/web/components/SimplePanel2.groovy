package test.web.components

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.panel.Panel

@CompileStatic
class SimplePanel2 extends Panel {
    SimplePanel2(String id) {
        super(id)
        setOutputMarkupId(true)
        listView('listView', ['one', 'two', 'three']) { item ->
            label('label').model(item.model)
        }
    }
}
