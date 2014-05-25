package wicket.groovy.core.components

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketFormTrait

@CompileStatic
class GroovyStatelessForm<T> extends StatelessForm<T> implements WicketFormTrait {
    GroovyStatelessForm(String id, IModel<T> model = null, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
