package wicket.groovy.core.helpers

import groovy.transform.CompileStatic

/**
 * Created by eugene on 8/16/14.
 */
@CompileStatic
class TypeSafeModelBuilder {
    String notation = ''

    def propertyMissing(String name) {
        notation += "${name}."
        this
    }

    String getNotation() {
        notation[0..-2]
    }
}
