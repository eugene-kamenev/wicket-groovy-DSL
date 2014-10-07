package wicket.groovy

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import wicket.groovy.gems.GroovyDataTable
import wicket.groovy.gems.InfiniteScrollDataTable


@CompileStatic
class WicketGemsDSL {

    /**
     * Shortcut for GroovyDataTable
     * @param parent
     * @param id
     * @param closure
     * @return
     */
    static <T extends GroovyDataTable> T table(parent, String id,
                                               @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                               @ClosureParams(value = FromString, options = 'T')
                                                       Closure closure = null) {
        build(parent, GroovyDataTable.create(id, closure)) as T
    }

    /**
     * Shortcut for InfiniteScrollDataTable
     * @param parent
     * @param id
     * @param closure
     * @return
     */
    static <T extends InfiniteScrollDataTable> T infiniteTable(parent, String id,
                                                               @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                               @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, InfiniteScrollDataTable.create(id, closure)) as T
    }

    /**
     * Default component build method
     * @param parent
     * @param child
     * @param closure
     * @return
     */
    private static <T extends Component> T build(parent, T child,
                                                 @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                 @ClosureParams(value = FromString, options = 'T')
                                                         Closure closure = null) {
        if (parent instanceof MarkupContainer) {
            parent?.addOrReplace(child)
        }
        closure?.resolveStrategy = Closure.DELEGATE_FIRST
        closure?.delegate = child
        closure?.call(child)
        child
    }
}
