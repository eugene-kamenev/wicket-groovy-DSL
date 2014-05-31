package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyCheckBoxChoice extends CheckBox implements WicketComponentTrait {
    GroovyCheckBoxChoice(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyCheckBoxChoice(String id, IModel model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
