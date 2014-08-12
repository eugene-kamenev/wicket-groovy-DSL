package wicket.groovy.core.components.form
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.StatelessForm
import wicket.groovy.core.traits.WicketFormTrait
/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyStatelessForm<T> extends StatelessForm<T> implements WicketFormTrait {
}
