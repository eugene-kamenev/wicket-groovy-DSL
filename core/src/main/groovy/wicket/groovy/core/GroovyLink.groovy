package wicket.groovy.core

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketLinkTrait

@CompileStatic
class GroovyLink<T> extends Link<T> implements WicketLinkTrait {

    GroovyLink(String id, IModel<T> model = null, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
