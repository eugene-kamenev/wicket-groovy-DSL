package test.web
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.Model
import wicket.groovy.GroovyDSL

class HomePage extends WebPage {
    final String simpleText = 'Hello World!'

    private int i

    HomePage() {
        use(GroovyDSL) {
            div('simpleDiv') { MarkupContainer dd ->
                dd.bookmarkLink('link', AnotherPage) { Link link ->
                    link.label('label', Model.of('To Another Page'))

                }
                dd.ajaxLink('ajaxLink', [click: { AjaxRequestTarget target ->
                    println "i=${i}"
                    i++
                    def newPanel = (i % 2 == 0) ? new SimplePanel('replacePanel') : new SimplePanel2('replacePanel')
                    println newPanel.class.name
                    newPanel.setOutputMarkupId(true)
                    newPanel = page.get 'replacePanel' replaceWith newPanel
                    target.add(newPanel)
                }])
                add(new SimplePanel('replacePanel').setOutputMarkupId(true))
            }
        }
    }
}
