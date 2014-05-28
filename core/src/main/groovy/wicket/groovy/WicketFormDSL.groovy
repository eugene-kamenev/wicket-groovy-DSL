package wicket.groovy

import org.apache.wicket.Component
import org.apache.wicket.markup.html.form.*
import org.apache.wicket.model.IModel
import wicket.groovy.core.components.form.*

/**
 * @author Eugene Kamenev @eugenekamenev
 */
class WicketFormDSL {
    static <T> TextField<T> text(Component parent, String id, IModel<T> model = null, Map<String, Closure> override = null) {
        def child = new GroovyTextField<T>(id, model, override)
        parent?.add child
        child
    }

    static PasswordTextField password(Component parent, String id, IModel model = null, Map<String, Closure> override = null) {
        def child = new GroovyPasswordTextField(id, model, override)
        parent?.add child
        child
    }

    static EmailTextField email(Component parent, String id, IModel model = null, Map<String, Closure> override = null) {
        def child = new GroovyEmailField(id, model, override)
        parent?.add child
        child
    }

    static UrlTextField url(Component parent, String id, IModel model = null, Map<String, Closure> override = null) {
        def child = new GroovyUrlField(id, model, override)
        parent?.add child
        child
    }

    static TextField<Number> number(Component parent, String id, IModel<Number> model = null, Map<String, Closure> override = null) {
        def child = new GroovyTextField<Number>(id, model, Number, override)
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
