package wicket.groovy

import groovy.transform.CompileStatic
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.html.form.*
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.validator.UrlValidator
import wicket.groovy.core.components.form.*

/**
 * @author Eugene Kamenev @eugenekamenev
 */
@CompileStatic
class WicketFormDSL extends WicketDSL {

    static <T> TextField<T> text(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyTextField<T>(id, params?.model as IModel<T>, params?.type as Class<T>, override(params))
        parent?.add child
        closure?.call child
        child
    }

    static PasswordTextField password(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyPasswordTextField(id, params?.model as IModel, override(params))
        parent?.add child
        closure?.call child
        child
    }

    static EmailTextField email(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyEmailField(id, params?.model as IModel, override(params))
        parent?.add child
        closure?.call child
        child
    }

    static UrlTextField url(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyUrlField(id, params?.model as IModel<String>, params?.validator as UrlValidator ?: new UrlValidator(), override(params))
        parent?.add child
        closure?.call child
        child
    }

    static TextField<Number> number(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyTextField<Number>(id, params?.model as IModel<Number>, Number, override(params))
        parent?.add child
        closure?.call child
        child
    }

    static <T> DropDownChoice<T> dropDown(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def dropDown
        def renderer = null
        if (params?.renderer) {
            renderer = params?.renderer as IChoiceRenderer
        }
        if (params?.render) {
            renderer = choiceRender(null, params?.render as Map<String, String>)
        }
        if (!renderer) {
            renderer = new ChoiceRenderer<T>()
        }
        if (params?.choices instanceof List) {
            dropDown = new GroovyDropDownChoice(id, params?.model as IModel, params?.choices as List<T>, renderer as IChoiceRenderer, override(params))
        } else if (params?.choices instanceof IModel) {
            dropDown = new GroovyDropDownChoice(id, params?.model as IModel, params?.choices as IModel<List<T>>, renderer as IChoiceRenderer, override(params))
        } else {
            dropDown = new GroovyDropDownChoice(id)
        }
        parent?.add dropDown
        closure?.call dropDown
        dropDown
    }

    static <T> RadioChoice<T> radioChoice(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def radioChoice
        def renderer = null
        if (params?.renderer) {
            renderer = params?.renderer as IChoiceRenderer
        }
        if (params?.render) {
            renderer = choiceRender(null, params?.render as Map<String, String>)
        }
        if (!renderer) {
            renderer = new ChoiceRenderer<T>()
        }
        if (params?.choices instanceof List) {
            radioChoice = new GroovyRadioChoice(id, params?.model as IModel, params?.choices as List<T>, renderer as IChoiceRenderer, override(params))
        } else if (params?.choices instanceof IModel) {
            radioChoice = new GroovyRadioChoice(id, params?.model as IModel, params?.choices as IModel<List<T>>, renderer as IChoiceRenderer, override(params))
        } else {
            radioChoice = new GroovyRadioChoice(id)
        }
        parent?.add radioChoice
        closure?.call radioChoice
        radioChoice
    }

    static CheckBox checkBox(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def checkBox = new GroovyCheckBoxChoice(id, params?.model as IModel, override(params))
        parent?.add checkBox
        closure?.call checkBox
        checkBox
    }

    static <T> CheckBoxMultipleChoice<T> checkChoices(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def checkBoxMultipleChoice
        def renderer = null
        if (params?.renderer) {
            renderer = params?.renderer as IChoiceRenderer
        }
        if (params?.render) {
            renderer = choiceRender(null, params?.render as Map<String, String>)
        }
        if (!renderer) {
            renderer = new ChoiceRenderer<T>()
        }
        if (params?.choices instanceof List) {
            checkBoxMultipleChoice = new GroovyCheckBoxMultipleChoice(id, params?.model as IModel, params?.choices as List<T>, renderer as IChoiceRenderer, override(params))
        } else if (params?.choices instanceof IModel) {
            checkBoxMultipleChoice = new GroovyCheckBoxMultipleChoice(id, params?.model as IModel, params?.choices as IModel<List<T>>, renderer as IChoiceRenderer, override(params))
        } else {
            checkBoxMultipleChoice = new GroovyCheckBoxMultipleChoice(id)
        }
        parent?.add checkBoxMultipleChoice
        closure?.call checkBoxMultipleChoice
        checkBoxMultipleChoice
    }

    static <T> IChoiceRenderer<T> choiceRender(DropDownChoice choice, Map<String, String> objectProperty) {
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

    static Map<String, Closure> override(Map<String, Object> map) {
        map?.findAll { it.value instanceof Closure }
    }
}
