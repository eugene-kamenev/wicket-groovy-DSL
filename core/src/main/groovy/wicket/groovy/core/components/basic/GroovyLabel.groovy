package wicket.groovy.core.components.basic

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyLabel extends Label implements WicketComponentTrait {

    GroovyLabel(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyLabel(String id, String label, Map<String, Closure> override = null) {
        super(id, label)
        this.override = override
    }

    GroovyLabel(String id, Serializable label, Map<String, Closure> override = null) {
        super(id, label)
        this.override = override
    }

    GroovyLabel(String id, IModel<?> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
