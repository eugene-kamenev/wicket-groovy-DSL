package wicket.groovy.core.traits

trait WicketFormTrait extends WicketComponentTrait {
    void onSubmit() {
        override?.submit ? override.submit(this) : super.onSubmit()
    }

    void onValidate() {
        override?.validate ? override.validate(this) : super.onValidate()
    }

    void onError() {
        override?.error ? override.error(this) : super.onError()
    }
}
