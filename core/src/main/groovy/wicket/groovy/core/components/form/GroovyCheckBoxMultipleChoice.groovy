package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyCheckBoxMultipleChoice<T> extends CheckBoxMultipleChoice<T> implements WicketComponentTrait<T> {
}
