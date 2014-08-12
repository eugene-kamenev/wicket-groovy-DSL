package wicket.groovy.core.components.form

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.form.EmailTextField
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyEmailField extends EmailTextField implements WicketComponentTrait {
}
