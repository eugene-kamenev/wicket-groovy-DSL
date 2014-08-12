package wicket.groovy.core.components.basic

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
class GroovyListView<T> extends ListView<T> implements WicketComponentTrait<T> {
    Closure onItem

    GroovyListView(String id, List<T> list, Closure closure = null) {
        super(id, list as List<T>)
        this.onItem = closure
    }

    GroovyListView(String id, IModel<List<T>> list, Closure closure = null) {
        super(id, list)
        this.onItem = closure
    }

    @Override
    protected void populateItem(ListItem<T> item) {
        this.onItem?.resolveStrategy = Closure.DELEGATE_FIRST
        this.onItem?.delegate = item
        this.onItem?.call(item)
    }
}
