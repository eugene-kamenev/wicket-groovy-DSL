package test.web

import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

abstract class TemplatePage extends WebPage {
    static final def bootstrapUrl = 'http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css'
    static final def animateCss = new CssUrlReferenceHeaderItem(bootstrapUrl, 'screen', '')

    TemplatePage() {
    }

    TemplatePage(PageParameters parameters) {
        super(parameters)
    }

    TemplatePage(IModel<?> model) {
        super(model)
    }

    @Override
    protected void onInitialize() {
        super.onInitialize()
        add(new BookmarkablePageLink('home', HomePage))
        add(new BookmarkablePageLink('managePersons', ManagePersons))
        add(new BookmarkablePageLink('manageDepartments', ManageDepartments))
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(animateCss)
    }
}
