package wicket.groovy.core.components.ajax
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.ajax.markup.html.AjaxLink
import wicket.groovy.core.traits.WicketLinkTrait
/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyAjaxLink<T> extends AjaxLink<T> implements WicketLinkTrait<T> {
}
