package wicket.groovy.gorm.core.components

import org.apache.wicket.markup.repeater.data.IDataProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import wicket.groovy.gorm.core.domain.GormEntity

class GormDataProvider<T> implements IDataProvider<T> {
    private final Map<String, Object> params;

    GormDataProvider(Map<String, Object> params) {
        this.params = params
    }

    @Override
    Iterator<? extends T> iterator(long first, long count) {
        def parameters = params?.params as Map
        if (!parameters) parameters = [:]
        if (params?.query) {
            def target = params?.target as Class<GormEntity>
            target.executeQuery("${params?.query}", parameters + [max: count, offset: first]).iterator()
        } else {
            if (params?.criteria instanceof Closure) {
                def c = params.criteria.call()
                c.list(parameters + [max: count, offset: first]).iterator()
            }
        }
    }

    @Override
    long size() {
        def parameters = params?.params as Map
        if (!parameters) parameters = [:]
        if (params?.query) {
            def target = params?.target as Class<GormEntity>
            target.executeQuery("select count(*) ${params?.query}", parameters).first() as long
        } else {
            if (params?.criteria instanceof Closure) {
                params.criteria.call().count() as Integer
            }
        }
    }

    @Override
    IModel<T> model(T object) {
        new LoadableDetachableModel<T>(object) {
            @Override
            protected T load() {
                return this.getObject()
            }
        }
    }

    @Override
    void detach() {
    }
}
