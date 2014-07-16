package wicket.groovy.core.components.form
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.TextField
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyTextField<T> extends TextField<T> implements WicketComponentTrait<T> {
}
