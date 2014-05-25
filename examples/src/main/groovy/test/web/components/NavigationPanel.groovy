package test.web.components

import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.Model
import test.web.FormsPage
import test.web.ListViewPage
import test.web.VariousPage
import wicket.groovy.GroovyDSL

class NavigationPanel extends Panel {
    NavigationPanel(String id) {
        super(id)
        use(GroovyDSL){
            bookmarkLink('forms', FormsPage).label('label', Model.of('Forms Example'))
            bookmarkLink('lists', ListViewPage).label('label', Model.of('ListView Example'))
            bookmarkLink('various', VariousPage).label('label', Model.of('Various Examples'))
        }
    }
}
