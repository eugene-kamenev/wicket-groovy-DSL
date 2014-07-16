package wicket.groovy.core.components.basic
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.link.Link
import wicket.groovy.core.traits.WicketLinkTrait
/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyLink<T> extends Link<T> implements WicketLinkTrait<T> {
}
