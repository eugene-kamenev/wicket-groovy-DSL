package wicket.groovy.core.traits

import org.apache.wicket.ajax.AjaxRequestTarget

/**
 * Basic component trait, that overrides
 * link, ajaxlink default methods
 *
 * @author Eugene Kamenev @eugenekamenev
 */
trait WicketLinkTrait<T> extends WicketComponentTrait<T> {

    void onClick(AjaxRequestTarget target) {
        if (this.override?.click) {
            if (this.override.click.maximumNumberOfParameters > 1) {
                this.override.click.call(target, this)
            } else {
                this.override.click.call(target)
            }
        } else {
            super.onClick(target)
        }
    }

    void onClick() {
        if (this.override?.click) {
            this.override.click.delegate = this;
            this.override.click.call(this)
        } else {
            super.onClick()
        }
    }
}
