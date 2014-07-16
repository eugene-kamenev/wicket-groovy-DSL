package wicket.groovy.core.components.form
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.UrlTextField
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyUrlField<T> extends UrlTextField implements WicketComponentTrait<T> {
}
