package wicket.groovy.core

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

@CompileStatic
class GroovyWebMarkupContainer<T> extends WebMarkupContainer implements WicketComponentTrait {

    GroovyWebMarkupContainer(String id, IModel<T> model = null, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
