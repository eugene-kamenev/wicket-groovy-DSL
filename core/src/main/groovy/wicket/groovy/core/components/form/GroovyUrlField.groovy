package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.UrlTextField
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.validator.UrlValidator
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyUrlField extends UrlTextField implements WicketComponentTrait {

    GroovyUrlField(String id, String url, Map<String, Closure> override = null) {
        super(id, url)
        this.override = override
    }

    GroovyUrlField(String id, IModel<String> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }

    GroovyUrlField(String id, IModel<String> model, UrlValidator urlValidator, Map<String, Closure> override = null) {
        super(id, model, urlValidator)
        this.override = override
    }
}
