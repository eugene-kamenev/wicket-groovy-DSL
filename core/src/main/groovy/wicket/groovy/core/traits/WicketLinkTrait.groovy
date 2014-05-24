package wicket.groovy.core.traits

import org.apache.wicket.ajax.AjaxRequestTarget

trait WicketLinkTrait extends WicketComponentTrait {
    void onClick(AjaxRequestTarget target) {
        override?.click ? callClosure(override.click, target, this) : null
    }

    void onClick() {
        override?.click ? override.click(this) : null
    }
}
