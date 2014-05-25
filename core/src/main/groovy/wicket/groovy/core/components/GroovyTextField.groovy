package wicket.groovy.core.components

import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

class GroovyTextField<T> extends TextField<T> implements WicketComponentTrait {
    GroovyTextField(String id, IModel<T> model, Class<T> type, Map<String, Closure> override = null) {
        super(id, model, type)
        this.override = override
    }

    GroovyTextField(String id, IModel<T> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }

    GroovyTextField(String id, Class<T> type, Map<String, Closure> override = null) {
        super(id, type)
        this.override = override
    }

    GroovyTextField(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }
}
