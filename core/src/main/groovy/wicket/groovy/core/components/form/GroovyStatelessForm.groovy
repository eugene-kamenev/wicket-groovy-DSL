package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketFormTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyStatelessForm<T> extends StatelessForm<T> implements WicketFormTrait {

    GroovyStatelessForm(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyStatelessForm(String id, IModel<T> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
