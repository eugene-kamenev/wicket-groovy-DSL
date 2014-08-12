package test.web.components

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.panel.Panel

class SimplePanel extends Panel {
    SimplePanel(String id) {
        super(id)
        ajaxLink('ajaxLink') {
            label('label').model {
                new Random().nextInt() % 2 == 0 ?
                        'even'.toLoadModel() :
                        'odd'.toLoadModel()
            }
            click { AjaxRequestTarget target ->
                target.appendJavaScript('alert(\'please toggle the panel\')')
            }
        }

    }
}
