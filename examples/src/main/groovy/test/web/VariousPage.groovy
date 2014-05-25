package test.web
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import test.web.components.SimplePanel
import test.web.components.SimplePanel2
import wicket.groovy.WicketDSL
import wicket.groovy.WicketFormDSL

class VariousPage extends TemplatePage {
    private int i
    private String juname
    private String jup

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL, WicketFormDSL) {
            div('simpleDiv') { MarkupContainer mk ->
                mk.ajaxLink('ajaxLink', [click: { AjaxRequestTarget target ->
                    i++
                    def newPanel = (i % 2 == 0) ? new SimplePanel('replacePanel') : new SimplePanel2('replacePanel')
                    newPanel.setOutputMarkupId(true)
                    newPanel = page.get 'replacePanel' replaceWith newPanel
                    target.add(newPanel)
                }])
                this << new SimplePanel('replacePanel').setOutputMarkupId(true)

            }
        }
    }
}
