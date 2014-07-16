package wicket.groovy.core.components.ajax
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import wicket.groovy.core.traits.WicketFormTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyAjaxButton<T> extends AjaxButton implements WicketFormTrait<T> {
}
