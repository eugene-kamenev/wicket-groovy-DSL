package wicket.groovy.core.model

import org.apache.wicket.model.IModel

/**
 * This model is possible, but I am not sure
 * how it will be Serialized
 * You can construct it with:
 * def model = new ClosureModel(getter: {}, setter: {})
 * @param < T >
 */
class ClosureModel<T> implements IModel<T> {
    Closure<Void> setter
    Closure<T> getter

    @Override
    T getObject() {
        getter.call()
    }

    @Override
    void setObject(T object) {
        setter.call(object)
    }

    @Override
    void detach() {
    }
}
