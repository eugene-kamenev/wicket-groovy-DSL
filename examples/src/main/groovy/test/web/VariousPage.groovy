package test.web

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.Component
import org.apache.wicket.ajax.AjaxRequestTarget
import test.web.components.SimplePanel
import test.web.components.SimplePanel2

@CompileStatic
@InheritConstructors
class VariousPage extends TemplatePage {
    def i = 0
    def juname
    def jup
    Component replacePanel = new SimplePanel('replacePanel').setOutputMarkupId(true)

    @Override
    void onInitialize() {
        super.onInitialize()
        div('simpleDiv') {
            ajaxLink('ajaxLink') {
                click { AjaxRequestTarget target ->
                    def newPanel = new Random().nextBoolean() ? new SimplePanel('replacePanel') : new SimplePanel2('replacePanel')
                    newPanel = page.get('replacePanel') >> (newPanel as Component)
                    target.add(newPanel)
                }
            }
        }
        this + replacePanel
    }
}
