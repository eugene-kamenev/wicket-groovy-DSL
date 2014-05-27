package test.web

import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage

abstract class TemplatePage extends WebPage {
    static final def bootstrapUrl = 'http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css'
    static final def animateCss = new CssUrlReferenceHeaderItem(bootstrapUrl, 'screen', '')

    @Override
    protected void onInitialize() {
        super.onInitialize()
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(animateCss)
    }
}
