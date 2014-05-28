package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketFormTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyForm<T> extends Form<T> implements WicketFormTrait {

    GroovyForm(String id, IModel<T> model = null, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
