package wicket.groovy.gorm.core.components

import groovy.transform.CompileStatic
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.markup.repeater.data.DataView
import org.apache.wicket.markup.repeater.data.IDataProvider
import wicket.groovy.core.traits.WicketComponentTrait
import wicket.groovy.gorm.WicketGormDSL

class GormDataView<T> extends DataView<T> implements WicketComponentTrait {

    GormDataView(String id, IDataProvider<T> dataProvider, Map<String, Closure> override = null, Closure closure = null) {
        this(id, dataProvider, override?.max?.call() as Long ?: 10L)
        this.override = override
        this.closure = closure
    }

    GormDataView(String id, IDataProvider<T> dataProvider, long itemsPerPage) {
        super(id, dataProvider, itemsPerPage)
    }

    @Override
    protected void populateItem(Item<T> item) {
        use(WicketGormDSL) {
            this.closure.call(item)
        }
    }
}
