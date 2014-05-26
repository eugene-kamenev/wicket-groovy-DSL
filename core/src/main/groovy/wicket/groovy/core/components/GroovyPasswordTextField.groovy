package wicket.groovy.core.components

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

@CompileStatic
class GroovyPasswordTextField extends PasswordTextField implements WicketComponentTrait {
    GroovyPasswordTextField(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyPasswordTextField(String id, IModel<String> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
