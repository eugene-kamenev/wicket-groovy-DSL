package wicket.groovy.core.components.basic
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.panel.Fragment
import wicket.groovy.core.traits.WicketComponentTrait

@CompileStatic
@InheritConstructors
class GroovyFragment<T> extends Fragment implements WicketComponentTrait<T> {
}
