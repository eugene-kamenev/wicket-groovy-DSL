package wicket.groovy

import org.apache.wicket.Component
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel
import wicket.groovy.core.components.form.GroovyDropDownChoice
import wicket.groovy.core.components.form.GroovyPasswordTextField
import wicket.groovy.core.components.form.GroovyTextField

class WicketFormDSL {
    static <T> TextField<T> field(Component parent, String id, IModel<T> model = null, Map<String, Closure> override = null) {
        def child = new GroovyTextField<T>(id, model, override)
        parent?.add child
        child
    }

    static PasswordTextField pfield(Component parent, String id, IModel model = null, Map<String, Closure> override = null) {
        def child = new GroovyPasswordTextField(id, model, override)
        parent?.add child
        child
    }

    static <T> DropDownChoice dropDown(Component parent, String id, List choices, Map<String, String> choiceRenderer = null, Map<String, String> override = null) {
        def dropDown = new GroovyDropDownChoice(id, choices, choiceRender(null, choiceRenderer), override)
        parent?.add dropDown
        dropDown
    }

    static <T> IChoiceRenderer choiceRender(DropDownChoice choice, Map<String, String> objectProperty) {
        def choiceRenderer = new IChoiceRenderer() {
            @Override
            Object getDisplayValue(Object object) {
                return object[objectProperty?.value]
            }

            @Override
            String getIdValue(Object object, int index) {
                return object[objectProperty?.id]
            }
        }
        choice?.choiceRenderer = choiceRenderer
        choiceRenderer
    }
}
