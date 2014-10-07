package wicket.groovy.core.components.basic
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
@InheritConstructors
class GroovyBookmarkablePageLink extends BookmarkablePageLink implements WicketComponentTrait<Void> {
}
