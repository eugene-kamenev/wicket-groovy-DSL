package test.web.components

import org.apache.wicket.markup.html.panel.Panel
import test.web.FormsPage
import test.web.ListViewPage
import test.web.LoginPage
import test.web.VariousPage

class NavigationPanel extends Panel {
    NavigationPanel(String id) {
        super(id)
        bookmarkLink('forms', FormsPage) {
            label('label').model 'Forms Example'.toLoadModel()
        }
        bookmarkLink('lists', ListViewPage) {
            label('label').model 'ListView Example'.toLoadModel()
        }
        bookmarkLink('various', VariousPage) {
            label('label').model('Various Examples'.toLoadModel())
        }
        bookmarkLink('login', LoginPage) {
            label('label') {
                model 'Login Example'.toLoadModel()
            }
        }
    }
}
