package test.web

import org.apache.wicket.ajax.AjaxRequestTarget
import test.web.components.SimplePanel
import test.web.components.SimplePanel2

class VariousPage extends TemplatePage {
    private int i = 0
    private String juname
    private String jup

    @Override
    protected void onInitialize() {
        super.onInitialize()
        def replacePanel = new SimplePanel('replacePanel').setOutputMarkupId(true)
        div('simpleDiv') {
            ajaxLink('ajaxLink') {
                click { AjaxRequestTarget target ->
                    this.i++
                    def newPanel = (this.i % 2 == 0) ? new SimplePanel('replacePanel') : new SimplePanel2('replacePanel')
                    newPanel.setOutputMarkupId(true)
                    newPanel = page.get('replacePanel') >> newPanel
                    target.add(newPanel)
                }
            }
        }
        this + replacePanel
    }
}
