package test.web

import groovy.transform.CompileStatic
import org.apache.wicket.devutils.debugbar.DebugBar
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem
import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import test.web.components.NavigationPanel

@CompileStatic
abstract class TemplatePage extends WebPage {
    static final String bootstrapUrl = 'http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css'
    static final def animateCss = new CssUrlReferenceHeaderItem(bootstrapUrl, 'screen', '')

    TemplatePage() {
    }

    TemplatePage(IModel model) {
        super(model)
    }

    TemplatePage(PageParameters parameters) {
        super(parameters)
    }

    @Override
    protected void onInitialize() {
        super.onInitialize()
        this + new DebugBar('debugPanel')
        this + new NavigationPanel('navigation')
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(animateCss as HeaderItem)
    }
}
