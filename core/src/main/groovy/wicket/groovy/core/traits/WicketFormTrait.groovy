package wicket.groovy.core.traits

import wicket.groovy.WicketDSL

/**
 * @author Eugene Kamenev @eugenekamenev
 */
trait WicketFormTrait extends WicketComponentTrait {
    void onSubmit() {
        override?.submit ? use(WicketDSL) { override.submit(this) } : super.onSubmit()
    }

    void onValidate() {
        this.updateFormComponentModels();
        override?.validate ? use(WicketDSL) { override.validate(this) } : super.onValidate()
    }

    void onError() {
        override?.error ? use(WicketDSL) { override.error(this) } : super.onError()
    }
}
