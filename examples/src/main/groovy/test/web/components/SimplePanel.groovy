package test.web.components

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.Model
import wicket.groovy.WicketDSL

class SimplePanel extends Panel {
    SimplePanel(String id) {
        super(id)
        use(WicketDSL) {
            ajaxLink('ajaxLink', [click: { AjaxRequestTarget target, AjaxLink link ->
                target.appendJavaScript('alert(\'how are you?\')')
            }]) { AjaxLink m ->
                m.label('label', [model: Model.of('Click Me!')])
            }
        }
    }
}
