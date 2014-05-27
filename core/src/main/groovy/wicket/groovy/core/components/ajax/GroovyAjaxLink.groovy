package wicket.groovy.core.components.ajax

import groovy.transform.CompileStatic
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketLinkTrait

@CompileStatic
class GroovyAjaxLink<T> extends AjaxLink<T> implements WicketLinkTrait {

    GroovyAjaxLink(String id, IModel<T> model = null, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }
}
