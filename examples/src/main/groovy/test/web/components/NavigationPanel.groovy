package test.web.components
import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.panel.Panel
import test.web.*

@CompileStatic
class NavigationPanel extends Panel {
    NavigationPanel(String id) {
        super(id)
        bookmarkLink('forms', FormsPage).label('label').model('Forms Example'.toModel())
        bookmarkLink('lists', ListViewPage).label('label').model('ListView Example'.toModel())
        bookmarkLink('various', VariousPage).label('label').model('Various Examples'.toModel())
        bookmarkLink('login', LoginPage).label('label').model('Login Example'.toModel())
        bookmarkLink('gems', GemsPage).label('label').model('GemsPage'.toModel())
    }
}
