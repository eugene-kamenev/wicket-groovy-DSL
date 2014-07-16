package wicket.groovy.core.components.form
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.DropDownChoice
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyDropDownChoice<T> extends DropDownChoice<T> implements WicketComponentTrait<T> {
}
