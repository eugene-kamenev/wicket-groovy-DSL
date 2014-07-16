package wicket.groovy.core.traits
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.model.IModel
/**
 * @author Eugene Kamenev @eugenekamenev
 */
@CompileStatic
trait WicketComponentTrait<M> implements WicketComponent<M>, Serializable {
    private Map<String, Closure> override

    WicketComponent<M> click(
            @DelegatesTo(value = WicketComponent, strategy = Closure.DELEGATE_FIRST) Closure<?> click) {
        this.getOverride().click = click
        this
    }

    WicketComponent<M> submit(
            @DelegatesTo(value = WicketComponent, strategy = Closure.DELEGATE_FIRST) Closure<?> submit) {
        this.getOverride().submit = submit
        this
    }

    WicketComponent<M> visible(@DelegatesTo(WicketComponent) Closure<Boolean> visible) {
        this.getOverride().visible = visible
        this
    }

    WicketComponent<M> enabled(@DelegatesTo(WicketComponent) Closure<Boolean> enabled) {
        this.getOverride().enabled = enabled
        this
    }

    WicketComponent<M> model(Closure<IModel<?>> closure) {
        model(closure())
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    WicketComponent<M> model(IModel<?> model) {
        this.asComponent().setDefaultModel(model)
        this
    }

    MarkupContainer parent() {
        this.asComponent().getParent()
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    WicketComponent<M> with(components) {
        this.asContainer().add(components)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    IModel<M> model() {
        this.asComponent().getDefaultModel() as IModel<M>
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Component asComponent() {
        this as Component
    }

    MarkupContainer asContainer() {
        this as MarkupContainer
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    boolean isVisible() {
        this.getOverride()?.visible ? this.getOverride()?.visible?.call(this) : super.isVisible()
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    boolean isEnabled() {
        this.getOverride()?.enabled ? this.getOverride()?.enabled?.call(this) : super.isEnabled()
    }

    void setOverride(Map<String, Closure> override) {
        this.override = override;
    }

    Map<String, Closure> getOverride() {
        if (!this.override) {
            this.override = new LinkedHashMap<>()
        }
        this.override
    }
}
