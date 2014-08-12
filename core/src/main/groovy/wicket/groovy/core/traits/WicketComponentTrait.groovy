package wicket.groovy.core.traits
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.model.IModel
/**
 * Base wicket component trait
 *
 * @author Eugene Kamenev @eugenekamenev
 */
@CompileStatic
trait WicketComponentTrait<M> implements Serializable {
    /**
     * Map that holds closures for component
     */
    private Map<String, Closure> override

    /**
     * Click closure for Link, AjaxLink
     *
     * @param click
     * @return
     */
    def click(@DelegatesTo(value = WicketComponentTrait, strategy = Closure.DELEGATE_FIRST)
                      Closure click) {
        this.getOverride().click = click
        this
    }

    /**
     * Submit closure for Form, AjaxButton etc
     *
     * @param submit
     * @return
     */
    def submit(@DelegatesTo(value = WicketComponentTrait, strategy = Closure.DELEGATE_FIRST)
                       Closure submit) {
        this.getOverride().submit = submit
        this
    }

    /**
     * Boolean closure for component visibility state
     *
     * @param visible
     * @return
     */
    def visible(@DelegatesTo Closure<Boolean> visible) {
        this.getOverride().visible = visible
        this
    }

    /**
     * Boolean closure for component enabled state
     *
     * @param enabled
     * @return
     */
    def enabled(@DelegatesTo Closure<Boolean> enabled) {
        this.getOverride().enabled = enabled
        this
    }

    /**
     * IModel setter closure
     *
     * @param closure
     * @return
     */
    def model(Closure<IModel<?>> closure) {
        model(closure())
    }

    /**
     * IModel setter
     *
     * @param model
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    def model(IModel<?> model) {
        this.asComponent().setDefaultModel(model)
        this
    }

    /**
     * IModel getter
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    IModel<M> model() {
        this.asComponent().getDefaultModel() as IModel<M>
    }

    /**
     * Helper method for getting parent component
     *
     * @return org.apache.wicket.MarkupContainer
     */
    MarkupContainer parent() {
        this.asComponent().getParent()
    }

    /**
     * Helper method for adding collection components
     *
     * @param components collection/array
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    def with(components) {
        this.asContainer().add(components)
        this
    }

    /**
     * Helper method for casting to Component
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    Component asComponent() {
        this as Component
    }

    /**
     * Helper method for casting to MarkupContainer
     * @return
     */
    MarkupContainer asContainer() {
        this as MarkupContainer
    }

    /**
     * Overrides isVisible() method
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    boolean isVisible() {
        this.getOverride()?.visible ? this.getOverride()?.visible?.call(this) : super.isVisible()
    }

    /**
     * Overrides isVisible() method
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    boolean isEnabled() {
        this.getOverride()?.enabled ? this.getOverride()?.enabled?.call(this) : super.isEnabled()
    }

    /**
     * Override map setter
     * @param override
     */
    void setOverride(Map<String, Closure> override) {
        this.override = override;
    }

    /**
     * Override map getter
     * @return
     */
    Map<String, Closure> getOverride() {
        if (!this.override) {
            this.override = new LinkedHashMap<>()
        }
        this.override
    }
}
