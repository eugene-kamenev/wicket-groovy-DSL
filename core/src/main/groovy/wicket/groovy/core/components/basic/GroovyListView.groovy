package wicket.groovy.core.components.basic

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyListView<T> extends ListView<T> implements WicketComponentTrait<T> {
    transient Closure<?> onItem

    GroovyListView(String id, List<T> list, Closure<?> closure) {
        super(id, list)
        this.onItem = closure
    }

    GroovyListView(String id, IModel<List<T>> listModel, Closure<?> closure) {
        super(id, listModel)
        this.onItem = closure
    }

    @Override
    protected void populateItem(ListItem<T> item) {
        this.onItem?.delegate = item
        this.onItem?.resolveStrategy = Closure.DELEGATE_FIRST
        this.onItem?.call(item)
    }
}
