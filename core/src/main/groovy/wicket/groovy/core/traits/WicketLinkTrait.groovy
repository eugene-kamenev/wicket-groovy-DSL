package wicket.groovy.core.traits

import org.apache.wicket.ajax.AjaxRequestTarget
import wicket.groovy.WicketDSL

/**
 * @author  Eugene Kamenev @eugenekamenev
 */
trait WicketLinkTrait extends WicketComponentTrait {
    void onClick(AjaxRequestTarget target) {
        override?.click ? use(WicketDSL) { callClosure(override.click, target, this) } : null
    }

    void onClick() {
        override?.click ? use(WicketDSL) { override.click(this) } : null
    }
}
