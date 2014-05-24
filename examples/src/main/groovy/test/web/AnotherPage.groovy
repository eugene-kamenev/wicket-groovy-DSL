package test.web

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link.BookmarkablePageLink

class AnotherPage extends WebPage {
    AnotherPage() {
        add(new BookmarkablePageLink('backToHome', HomePage))
    }
}
