package wicket.groovy

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.Page
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior
import org.apache.wicket.ajax.AjaxEventBehavior
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.attributes.AjaxCallListener
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.AbstractChoice
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.*
import org.codehaus.groovy.runtime.InvokerHelper
import wicket.groovy.core.components.ajax.GroovyAjaxButton
import wicket.groovy.core.components.ajax.GroovyAjaxLink
import wicket.groovy.core.components.basic.*
import wicket.groovy.core.components.form.*

@CompileStatic
class WicketDSL<M extends Serializable> {

    static <C extends MarkupContainer, T extends GroovyWebMarkupContainer<M>> T div(C parent, String id,
                                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST) @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyWebMarkupContainer(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyImage<M>> T image(C parent, String id,
                                                                         @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                         @ClosureParams(value = FromString, options = 'T') Closure closure = null) {

        build(parent, new GroovyImage(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyFragment<M>> T fragment(C parent, String id, String markupId, boolean addToParent = true,
                                                                               @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                               @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        if (addToParent) {
            build(parent, new GroovyFragment(id, markupId, parent), closure)
        } else {
            build(null, new GroovyFragment(id, markupId, parent), closure)
        }
    }

    static <I extends ListItem<M>, C extends MarkupContainer, T extends GroovyListView<M>> T listView(C parent, String id,
                                                                                                      @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                      @ClosureParams(value = FromString, options = 'I')
                                                                                                              Closure closure = null) {
        build(parent, new GroovyListView<M>(id, null as List, closure)) as T
    }

    static <I extends ListItem<M>, C extends MarkupContainer, T extends GroovyListView<M>> T listView(C parent, String id, IModel<? extends List> model,
                                                                                                      @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                      @ClosureParams(value = FromString, options = 'I')
                                                                                                              Closure closure = null) {
        build(parent, new GroovyListView<M>(id, model, closure)) as T
    }

    static <I extends ListItem, C extends MarkupContainer, T extends GroovyListView<M>> T listView(C parent, String id, List list,
                                                                                                   @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                   @ClosureParams(value = FromString, options = 'I')
                                                                                                           Closure closure = null) {
        build(parent, new GroovyListView<M>(id, list, closure)) as T
    }


    static <I extends ListItem<M>, T extends GroovyListView<M>> T listView(List list, String id,
                                                                           @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                           @ClosureParams(value = FromString, options = 'I')
                                                                                   Closure onItem = null) {
        new GroovyListView<M>(id, list, onItem)
    }

    static <T> LoadableDetachableModel<T> loadModel(parent, Closure<T> closure) {
        new LoadableDetachableModel() {
            @Override
            protected Object load() {
                closure.call()
            }
        }
    }

    static <C extends MarkupContainer, T extends GroovyStatelessForm<M>> T statelessForm(C parent, String id,
                                                                                         @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                         @ClosureParams(value = FromString, options = 'T')
                                                                                                 Closure closure = null) {
        build(parent, new GroovyStatelessForm<M>(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyForm<M>> T form(C parent, String id,
                                                                       @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                       @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyForm<M>(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyForm<M>> T ajaxForm(C parent, String id,
                                                                           @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                           @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        def form = form(parent, id, closure)
        Closure submit = form.override.remove('submit')
        Closure error = form.override.remove('error')
        def submitButton = new GroovyAjaxButton<M>('submit')
        submitButton.override.submit = submit
        submitButton.override.error = error
        form.add submitButton
        form as T
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    static <C extends MarkupContainer, T extends GroovyBookmarkablePageLink> T bookmarkLink(C parent, String id, Class<? extends Page> target,
                                                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                            @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyBookmarkablePageLink(id, target, null), closure)
    }

    static <C extends MarkupContainer, M, T extends GroovyLabel<M>> T label(C parent, String id,
                                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                            @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyLabel(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyAjaxLink<M>> T ajaxLink(C parent, String id,
                                                                               @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                               @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyAjaxLink<M>(id), closure)
    }

    static <C extends MarkupContainer> IModel<M> model(C container) {
        container.getDefaultModel()
    }

    static <C extends MarkupContainer, T extends AjaxLink<M>> T ajaxConfirmLink(C parent, String id, Map<String, Object> params = null,
                                                                                @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        def child = new GroovyAjaxLink<M>(id) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener ajaxCallListener = new AjaxCallListener();
                ajaxCallListener.onPrecondition("return confirm('" + params?.text + "');");
                attributes.getAjaxCallListeners().add(ajaxCallListener);
            }
        }
        closure?.call(child)
        child as T
    }

    static <C extends MarkupContainer, T extends GroovyLink<M>> T link(C parent, String id,
                                                                       @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                       @ClosureParams(value = FromString, options = 'T')
                                                                               Closure closure = null) {
        build(parent, new GroovyLink<T>(id), closure)
    }

    static <T extends Component> T withModel(T container, IModel<M> model) {
        container.setDefaultModel(model)
    }

    static <C extends MarkupContainer, T extends Component> T and(C parent, T child) {
        parent.add(child)
    }

    static <T extends Component> Component withModel(T container, Closure<IModel<M>> modelClosure) {
        container.setDefaultModel(modelClosure.call())
    }

    static <C extends MarkupContainer, T extends GroovyTextField<M>> T text(C parent, String id,
                                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST) @ClosureParams(value = FromString, options = 'T')
                                                                                    Closure closure = null) {
        build(parent, new GroovyTextField(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyPasswordTextField> T password(C parent, String id,
                                                                                     @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                     @ClosureParams(value = FromString, options = 'T')
                                                                                             Closure closure = null) {
        build(parent, new GroovyPasswordTextField(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyEmailField> T email(C parent, String id,
                                                                           @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                           @ClosureParams(value = FromString, options = 'T')
                                                                                   Closure closure = null) {
        build(parent, new GroovyEmailField(id), closure);
    }

    static <C extends MarkupContainer, T extends GroovyUrlField> T url(C parent, String id, String url,
                                                                       @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                       @ClosureParams(value = FromString, options = 'T')
                                                                               Closure closure = null) {
        build(parent, new GroovyUrlField(id, url)) as T
    }

    static <C extends MarkupContainer, T extends GroovyTextField<BigDecimal>> T number(C parent, String id,
                                                                                       @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                       @ClosureParams(value = FromString, options = 'T')
                                                                                               Closure closure = null) {
        build(parent, new GroovyTextField(id, BigDecimal), closure)
    }

    static <C extends MarkupContainer, T extends GroovyDropDownChoice<M>> T dropDown(C parent, String id,
                                                                                     @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                     @ClosureParams(value = FromString, options = 'T')
                                                                                             Closure closure = null) {
        build(parent, new GroovyDropDownChoice<M>(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyRadioChoice<M>> T radioChoice(C parent, String id,
                                                                                     @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                     @ClosureParams(value = FromString, options = 'T')
                                                                                             Closure closure = null) {
        build(parent, new GroovyRadioChoice(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyCheckBoxChoice> T checkBox(C parent, String id,
                                                                                  @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                  @ClosureParams(value = FromString, options = 'T')
                                                                                          Closure closure = null) {
        build(parent, new GroovyCheckBoxChoice(id), closure)
    }

    static <C extends MarkupContainer, T extends GroovyCheckBoxMultipleChoice<M>> T checkChoices(C parent, String id,
                                                                                                 @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                                 @ClosureParams(value = FromString, options = 'T')
                                                                                                         Closure closure = null) {
        build(parent, new GroovyCheckBoxMultipleChoice<M>(id), closure)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    static <A, M, T extends AbstractChoice<M, A>> T choicesRender(
            final T choice, final Map objectProperty) {
        choice?.setChoiceRenderer(new IChoiceRenderer() {
            @Override
            Object getDisplayValue(Object object) {
                return object[objectProperty?.value]
            }

            @Override
            String getIdValue(Object object, int index) {
                return object[objectProperty?.id]
            }
        })
        choice
    }

    static <T extends GroovyDropDownChoice<M>> T toDropDown(IModel<? extends List<M>> choices, String id,
                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                            @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(null, new GroovyDropDownChoice<M>(id, null as IModel, choices), closure)
    }

    static <T extends GroovyDropDownChoice<M>> T toDropDown(List<M> choices, String id,
                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                            @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(null, new GroovyDropDownChoice<M>(id, null as IModel, choices), closure)
    }

    static <T extends GroovyLabel<M>> T toLabel(M o, String id,
                                                @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                @ClosureParams(value = FromString, options = 'T')
                                                        Closure closure = null) {
        toLabel(loadModel(o), id, closure) as T
    }

    static <T extends GroovyLabel<M>> T toLabel(IModel<M> model, String id,
                                                @DelegatesTo(value = T,
                                                        strategy = Closure.DELEGATE_FIRST)
                                                @ClosureParams(value = FromString, options = 'T')
                                                        Closure closure = null) {
        def label = label(null, id, closure)
        label.setDefaultModel(model)
        label as T
    }

    static <C extends Component, A extends AjaxRequestTarget> C ajaxBehavior(C component,
                                                                             @ClosureParams(value = FromString, options = 'A') Closure action) {
        component.add new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(AjaxRequestTarget target) {
                action.call(target)
            }
        }
    }

    static <C extends Component, A extends AjaxRequestTarget> C ajaxOnClick(C component,
                                                                            @ClosureParams(value = FromString, options = 'A') Closure action) {
        component.add new AjaxEventBehavior('onclick') {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                action.call(target)
            }
        }
    }

    static <T> IModel<T> loadModel(final T o) {
        new LoadableDetachableModel() {
            @Override
            protected Object load() {
                return o
            }
        }
    }

    static <T> IModel<T> entityModel(T object) {
        return new EntityModel<T>(object)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    static class EntityModel<T> extends LoadableDetachableModel<T> {
        Class<T> clazz
        Object id

        EntityModel(T object) {
            this.clazz = object.class
            this.id = object.id
        }

        @Override
        protected T load() {
            return InvokerHelper.invokeMethod(clazz, 'get', id) as T
        }
    }

    static <T extends Component> T css(T component, String... names) {
        css(component, names.toList())
    }

    static <T extends Component> T css(T component, List<String> list) {
        component.add(new AttributeAppender('class', Model.of(list.join(' ')))) as T
    }

    static <T extends Component> T css(T component, Closure<List<String>> closure) {
        css(component, closure.call())
    }

    static <T extends Component> T compoundModel(T component, IModel model) {
        component.setDefaultModel(new CompoundPropertyModel(model))
    }

    static <T extends Component> T compoundModel(T component, Component modelComponent) {
        component.setDefaultModel(new CompoundPropertyModel(modelComponent))
    }

    static <T> IModel<T> property(IModel model, String property) {
        new PropertyModel(model, property)
    }

    static <T> IModel<T> property(Object object, String property) {
        new PropertyModel(object, property)
    }

    static <C extends Component, T> IModel<T> property(C object, String property, boolean fromModel = false) {
        fromModel ?
                new PropertyModel(object.getDefaultModel(), property) : new PropertyModel(object, property)
    }

    static void replaceOnPage(AjaxRequestTarget t, String id, Component Component) {
        t.add(t.page.get(id)?.replaceWith(Component))
    }

    static <T> IModel<T> toLoadModel(final T object) {
        loadModel(null) {
            object
        }
    }

    private static <C extends MarkupContainer, T extends Component> T build(C parent, T child,
                                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                            @ClosureParams(value = FromString, options = 'T')
                                                                                    Closure closure = null) {
        parent?.add(child)
        closure?.resolveStrategy = Closure.DELEGATE_FIRST
        closure?.delegate = child
        closure?.call(child)
        child
    }

    static <T extends Component> T rightShift(T container, T another) {
        container?.replaceWith another
    }

    static <T extends MarkupContainer, C extends Component> T plus(T parent, C child) {
        parent?.add child
    }

    static <T extends MarkupContainer> T plus(T parent, Behavior behavior) {
        parent?.add(behavior) as T
    }

    static <T extends MarkupContainer, C extends Component> T minus(T parent, C child) {
        parent?.remove child
    }

    static <T extends MarkupContainer, C extends Component> T leftShift(T parent, C child) {
        parent?.addOrReplace child
    }

    static Map<String, Closure> override(Map<String, Object> map) {
        map?.findAll { it.value instanceof Closure }
    }
}
