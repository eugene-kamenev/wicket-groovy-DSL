package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.form.RadioChoice
import org.apache.wicket.model.IModel
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyRadioChoice<T> extends RadioChoice<T> implements WicketComponentTrait {
    GroovyRadioChoice(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyRadioChoice(String id, List choices, Map<String, Closure> override = null) {
        super(id, choices)
        this.override = override
    }

    GroovyRadioChoice(String id, List choices, IChoiceRenderer renderer, Map<String, Closure> override = null) {
        super(id, choices, renderer)
        this.override = override
    }

    GroovyRadioChoice(String id, IModel model, List choices, Map<String, Closure> override = null) {
        super(id, model, choices)
        this.override = override
    }

    GroovyRadioChoice(String id, IModel model, List choices, IChoiceRenderer renderer, Map<String, Closure> override = null) {
        super(id, model, choices, renderer)
        this.override = override
    }

    GroovyRadioChoice(String id, IModel<? extends List> choices, Map<String, Closure> override = null) {
        super(id, choices)
        this.override = override
    }

    GroovyRadioChoice(String id, IModel model, IModel<? extends List> choices, Map<String, Closure> override = null) {
        super(id, model, choices)
        this.override = override

    }

    GroovyRadioChoice(String id, IModel<? extends List> choices, IChoiceRenderer renderer, Map<String, Closure> override = null) {
        super(id, choices, renderer)
        this.override = override
    }

    GroovyRadioChoice(String id, IModel model, IModel<? extends List> choices, IChoiceRenderer renderer, Map<String, Closure> override = null) {
        super(id, model, choices, renderer)
        this.override = override
    }
}
