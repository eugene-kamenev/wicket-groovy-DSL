package wicket.groovy.core.traits

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.form.Form

/**
 * Basic component trait, that overrides
 * forms, buttons default methods
 *
 * @author Eugene Kamenev @eugenekamenev
 */
@CompileStatic
trait WicketFormTrait<T> extends WicketComponentTrait<T> {

    void onSubmit() {
        override?.submit ?
                override.submit.call(this) : null
    }

    void onSubmit(AjaxRequestTarget target, Form<?> form) {
        if (this.override?.submit) {
            if (this.override.submit.maximumNumberOfParameters > 1) {
                this.override.submit.call(target, form)
            } else {
                this.override.submit.call(target)
            }
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void onError(AjaxRequestTarget target, Form<?> form) {
        if (this.override?.error) {
            if (this.override.error.maximumNumberOfParameters > 1) {
                this.override.error.call(target, form)
            } else {
                this.override.error.call(target)
            }
        } else {
            super.onError(target, form)
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void onValidate() {
        if (override?.validate) {
            this.updateFormComponentModels();
            override.validate.call(this)
        } else {
            super.onValidate()
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void onError() {
        if (override?.error) {
            override.error.call(this)
        } else {
            super.onError()
        }
    }
}
