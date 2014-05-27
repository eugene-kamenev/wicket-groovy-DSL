package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.IChoiceRenderer
import wicket.groovy.core.traits.WicketComponentTrait

@CompileStatic
class GroovyDropDownChoice extends DropDownChoice implements WicketComponentTrait {
    GroovyDropDownChoice(String id, List choices, IChoiceRenderer renderer, Map<String, Closure> override) {
        super(id, choices, renderer)
        this.override = override
    }
}
