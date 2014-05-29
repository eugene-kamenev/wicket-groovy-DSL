package wicket.groovy.core.components.basic

import groovy.transform.CompileStatic
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.html.panel.Fragment
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyFragment extends Fragment implements WicketComponentTrait {
    GroovyFragment(String id, String markupId, MarkupContainer markupProvider, Map<String, Closure> override = null) {
        super(id, markupId, markupProvider)
        this.override = override
    }

    GroovyFragment(String id, String markupId, MarkupContainer markupProvider, IModel<?> model, Map<String, Closure> override = null) {
        super(id, markupId, markupProvider, model)
        this.override = override
    }
}
