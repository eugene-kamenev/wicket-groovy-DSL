package wicket.groovy

import org.apache.wicket.Component
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel
import wicket.groovy.core.components.GroovyPasswordTextField
import wicket.groovy.core.components.GroovyTextField

class WicketFormDSL {
    static <T> TextField<T> field(Component parent, String id, IModel<T> model = null, Map<String, Closure> override = null) {
        def child = new GroovyTextField<T>(id, model, override)
        parent?.add child
        child
    }

    static PasswordTextField pfield(Component parent, String id, IModel model = null, Map<String, Closure> override = null) {
        def child = new GroovyPasswordTextField(id, model, override)
        parent?.add child
        child
    }
}
