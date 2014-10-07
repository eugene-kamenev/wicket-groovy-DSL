package wicket.groovy.core.components.basic

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.basic.Label
import wicket.groovy.core.traits.WicketComponentTrait
/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyLabel<T> extends Label implements WicketComponentTrait<T> {
}
