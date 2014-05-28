package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.EmailTextField
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.IValidator
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyEmailField extends EmailTextField implements WicketComponentTrait {
    GroovyEmailField(String id, String emailAddress, Map<String, Closure> override = null) {
        super(id, emailAddress)
        this.override = override
    }

    GroovyEmailField(String id, IModel<String> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }

    GroovyEmailField(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyEmailField(String id, IModel<String> model, IValidator<String> emailValidator, Map<String, Closure> override = null) {
        super(id, model, emailValidator)
        this.override = override
    }
}
