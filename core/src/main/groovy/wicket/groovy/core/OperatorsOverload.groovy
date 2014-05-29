package wicket.groovy.core

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer

class OperatorsOverload {

    static Component rightShift(Component container, Component another) {
        container?.replaceWith another
    }

    static Component plus(Component parent, Component child) {
        parent?.add child
    }

    static Component minus(Component parent, Component child) {
        parent?.remove child
    }

    static Component leftShift(MarkupContainer parent, Component child) {
        parent?.addOrReplace child
    }
}
