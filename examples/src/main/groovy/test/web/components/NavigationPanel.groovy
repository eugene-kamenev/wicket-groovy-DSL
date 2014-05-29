package test.web.components

import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.Model
import test.web.FormsPage
import test.web.ListViewPage
import test.web.LoginPage
import test.web.VariousPage
import wicket.groovy.WicketDSL

class NavigationPanel extends Panel {
    NavigationPanel(String id) {
        super(id)
        use(WicketDSL) {
            bookmarkLink('forms', FormsPage).label('label', [model: Model.of('Forms Example')])
            bookmarkLink('lists', ListViewPage).label('label', [model: Model.of('ListView Example')])
            bookmarkLink('various', VariousPage).label('label', [model: Model.of('Various Examples')])
            bookmarkLink('login', LoginPage).label('label', [model: Model.of('Login Example')])
        }
    }
}
