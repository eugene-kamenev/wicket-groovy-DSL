package wicket.groovy.core.components.basic
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.WebMarkupContainer
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyWebMarkupContainer<T> extends WebMarkupContainer implements WicketComponentTrait<T> {
}
