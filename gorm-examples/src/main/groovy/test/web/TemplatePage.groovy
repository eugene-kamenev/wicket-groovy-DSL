package test.web

import org.apache.wicket.RestartResponseException
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.panel.Fragment
import org.apache.wicket.model.CompoundPropertyModel
import test.domain.Person
import wicket.groovy.WicketDSL
import wicket.groovy.WicketFormDSL

abstract class TemplatePage extends WebPage {
    static final def bootstrapUrl = 'http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css'
    static final def animateCss = new CssUrlReferenceHeaderItem(bootstrapUrl, 'screen', '')

    @Override
    protected void onInitialize() {
        super.onInitialize()
        add(new BookmarkablePageLink('home', HomePage))
        add(new BookmarkablePageLink('addPerson', AddPersonPage))
        add(new BookmarkablePageLink('addDepartment', AddDepartmentPage))
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(animateCss)
    }

    public Fragment personListFragment() {
        def fragment = WicketDSL.fragment(null, 'persons', 'fragment', [provider: this]) { Fragment fr ->
            use(WicketDSL, WicketFormDSL) {
                fr.listView('personListView', [model: loadModel { Person.list([fetch: [department: 'join']]) }])
                        { ListItem<Person> item ->
                            item.defaultModel = new CompoundPropertyModel<Person>(item.getModel())
                            item.label 'name'
                            item.label 'department.title'
                            item.ajaxLink('delete', [model: item.model, click: { art, AjaxLink<Person> link -> link.modelObject.delete(flush: true); throw new RestartResponseException(getPage().class) }])
                        }
            }
        }
        fragment
    }
}
