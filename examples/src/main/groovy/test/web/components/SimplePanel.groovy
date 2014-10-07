package test.web.components

import groovy.transform.CompileStatic
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.panel.Panel

@CompileStatic
class SimplePanel extends Panel {
    SimplePanel(String id) {
        super(id)
        setOutputMarkupId(true)
        ajaxLink('ajaxLink') {
            label('label').model {
                new Random().nextBoolean() ? 'even'.toLoadModel() :
                        'odd'.toLoadModel()
            }
            click { AjaxRequestTarget target ->
                target.appendJavaScript('alert(\'please toggle the panel\')')
            }
        }

    }
}
