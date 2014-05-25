package test.web
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import wicket.groovy.GroovyDSL

class VariousPage extends TemplatePage {
    private int i

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(GroovyDSL) {
            div('simpleDiv') { MarkupContainer mk ->
                mk.ajaxLink('ajaxLink', [click: { AjaxRequestTarget target ->
                    i++
                    def newPanel = (i % 2 == 0) ? new SimplePanel('replacePanel') : new SimplePanel2('replacePanel')
                    newPanel.setOutputMarkupId(true)
                    newPanel = page.get 'replacePanel' replaceWith newPanel
                    target.add(newPanel)
                }])
                add(new SimplePanel('replacePanel').setOutputMarkupId(true))
            }
        }
    }
}
