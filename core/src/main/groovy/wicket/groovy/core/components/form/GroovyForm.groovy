package wicket.groovy.core.components.form
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.Form
import wicket.groovy.core.traits.WicketFormTrait
/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyForm<T> extends Form<T> implements WicketFormTrait<T> {
}
