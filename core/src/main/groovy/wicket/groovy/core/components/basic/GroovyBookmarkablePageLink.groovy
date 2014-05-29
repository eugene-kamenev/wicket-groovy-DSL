package wicket.groovy.core.components.basic

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.request.mapper.parameter.PageParameters
import wicket.groovy.core.traits.WicketComponentTrait

/**
 * Extended by @eugenekamenev
 */
@CompileStatic
class GroovyBookmarkablePageLink extends BookmarkablePageLink implements WicketComponentTrait {

    GroovyBookmarkablePageLink(String id, Class<?> pageClass, Map<String, Closure> override = null) {
        super(id, pageClass)
        this.override = override
    }

    GroovyBookmarkablePageLink(String id, Class<?> pageClass, PageParameters parameters, Map<String, Closure> override = null) {
        super(id, pageClass, parameters)
        this.override = override
    }
}
