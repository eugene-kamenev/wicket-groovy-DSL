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
import org.apache.wicket.model.util.ListModel
import wicket.groovy.core.components.ajax.GroovyAjaxButton
import wicket.groovy.core.components.ajax.GroovyAjaxLink
import wicket.groovy.core.components.basic.*
import wicket.groovy.core.components.form.*
import wicket.groovy.core.helpers.TypeSafeModelBuilder
import wicket.groovy.core.model.EnumModel
/**
 * Groovy Wicket DSL class, main magic is here :)
 * Methods of this class auto-append to first parameter object instances by Groovy Extension Module
 * @author @eugenekamenev
 */
@CompileStatic
class WicketDSL {

    /**
     * Shortcut for WebMarkupContainer
     * Usage Example:
     * div('markupId') { div ->
     *     // another components here auto-attached to WebMarkupContainer
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyWebMarkupContainer<M>> T div(parent, String id,
                                                                                 @ClosureParams(value = FromString, options = 'T')
                                                                                 @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                         Closure closure = null) {
        build(parent, new GroovyWebMarkupContainer(id), closure)
    }

    /**
     * Shortcut for Image instance
     * Usage example:
     * image('markupId') { image ->
     *     // set image details here
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyImage<M>> T image(parent, String id,
                                                                      @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                      @ClosureParams(value = FromString, options = 'T') Closure closure = null) {

        build(parent, new GroovyImage(id), closure)
    }

    /**
     * Shortcut for Fragment instance
     * Usage example:
     * fragment('id', 'fragmentMarkupId') { fragment ->
     *     // fragment children here
     *}* @param parent
     * @param id
     * @param markupId
     * @param addToParent
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyFragment<M>> T fragment(parent, String id, String markupId, boolean addToParent = true,
                                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                            @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        if (addToParent) {
            build(parent, new GroovyFragment(id, markupId, parent as MarkupContainer), closure)
        } else {
            build(null, new GroovyFragment(id, markupId, parent as MarkupContainer), closure)
        }
    }

    /**
     * Shortcut for empty ListView
     * Usage example:
     * listView('markupId') { item ->
     *     // populateItem here
     *}* @param parent
     * @param id
     * @param onItem
     * @return
     */
    static <M extends Serializable, I extends ListItem<M>, T extends GroovyListView<M>> T listView(parent, String id,
                                                                                                   @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                   @ClosureParams(value = FromString, options = 'I')
                                                                                                           Closure onItem = null) {
        build(parent, new GroovyListView<M>(id, null as List, onItem)) as T
    }

    /**
     * Shortcut for ListView
     * Usage example:
     * listView('markupId', listModel) { item ->
     *     // populateItem here
     *}* @param parent
     * @param id
     * @param model
     * @param onItem
     * @return
     */
    static <M extends Serializable, I extends ListItem<M>, T extends GroovyListView<M>> T listView(parent, String id, IModel<? extends List> model,
                                                                                                   @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                   @ClosureParams(value = FromString, options = 'I')
                                                                                                           Closure onItem = null) {
        build(parent, new GroovyListView<M>(id, model, onItem)) as T
    }

    /**
     * Shortcut for ListView
     * Usage example:
     * listView('markupId', list) { item ->
     *     // populateItem here
     *}* @param parent
     * @param id
     * @param list
     * @param onItem
     * @return
     */
    static <M extends Serializable, I extends ListItem, T extends GroovyListView<M>> T listView(parent, String id, List list,
                                                                                                @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                @ClosureParams(value = FromString, options = 'I')
                                                                                                        Closure onItem = null) {
        build(parent, new GroovyListView<M>(id, list, onItem)) as T
    }

    /**
     * Shortcut for ListView created with List instance
     * Usage example:
     * list.listView('markupId') { item ->
     *     // populateItem here
     *}* @param list
     * @param id
     * @param onItem
     * @return
     */
    static <M extends Serializable, I extends ListItem<M>, T extends GroovyListView<M>> T listView(List list, String id,
                                                                                                   @DelegatesTo(value = I, strategy = Closure.DELEGATE_FIRST)
                                                                                                   @ClosureParams(value = FromString, options = 'I')
                                                                                                           Closure onItem = null) {
        new GroovyListView<M>(id, list, onItem)
    }

    /**
     * Shortcut for producing LoadableDetachableModel
     * Usage example:
     * def imodel = anyObject.loadModel {*      // modelObject return
     *}* @param optional
     * @param closure
     * @return
     */
    static <T> LoadableDetachableModel<T> loadModel(optional, Closure<T> closure) {
        new LoadableDetachableModel() {
            @Override
            protected Object load() {
                closure.call()
            }
        }
    }

    /**
     * Shortcut for Wicket stateless form
     * Usage example:
     * statelessForm('markupId') { form ->
     *     //form content here
     *     submit {*     // onSubmit
     *}*}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <T extends GroovyStatelessForm> T statelessForm(parent, String id,
                                                                                      @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                      @ClosureParams(value = FromString, options = 'T')
                                                                                              Closure closure = null) {
        build(parent, new GroovyStatelessForm(id), closure)
    }

    /**
     * Shortcut for Wicket stateful form
     * Usage example:
     * form('markupId') { form ->
     *     //form content here
     *     submit {*     // onSubmit
     *}*}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyForm<M>> T form(parent, String id,
                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                    @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyForm<M>(id), closure)
    }

    /**
     * Shortcut for form submitted by ajax button
     * Usage example:
     * ajaxForm('markupId') { form ->
     *     // form content here
     *     submit { AjaxRequestTarget target, Form form ->
     *
     *}* or
     * ajaxForm('markupId') { form ->
     *     // form content here
     *     submit { AjaxRequestTarget target ->
     *
     *}*
     * @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyForm<M>> T ajaxForm(parent, String id,
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

    /**
     * Shortcut for BookmarkablePageLink
     * Usage example:
     * bookmarkLink('markupId') { link ->
     *     // bookmarklink delegate
     *}* @param parent
     * @param id
     * @param target
     * @param closure
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    static <T extends GroovyBookmarkablePageLink> T bookmarkLink(parent, String id, Class<? extends Page> target,
                                                                                         @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                         @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyBookmarkablePageLink(id, target, null), closure)
    }

    /**
     * Shortcut for Label
     * Usage example:
     * label('markupId') { label ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyLabel<M>> T label(Object parent, String id,
                                                                      @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                      @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyLabel<M>(id), closure)
    }

    /**
     * Shortcut for AjaxLink
     * Usage example:
     * ajaxLink('markupId') { link ->
     *     // link content
     *     click { AjaxRequestTarget t, AjaxLink<T> link ->
     *
     *}*}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyAjaxLink<M>> T ajaxLink(parent, String id,
                                                                            @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                            @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(parent, new GroovyAjaxLink<M>(id), closure)
    }

    /**
     * Shortcut component model getter
     * Usage example:
     * def imodel = component.model()
     *
     * @param container
     * @return
     */
    static <M extends Serializable, C extends Component> IModel<M> model(C container) {
        container.getDefaultModel()
    }

    static <C extends Component> void model(C container, IModel model) {
        container.setDefaultModel(model)
    }

    /**
     * Shortcut for AjaxConfirmationLink
     * Usage example:
     * ajaxConfirmLink('markupId', text) {*  click { AjaxRequestTarget t, AjaxLink<T> link ->
     *         // click logic here
     *}* @param parent
     * @param id
     * @param params
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends AjaxLink<M>> T ajaxConfirmLink(parent, String id, String text,
                                                                             @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                             @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        def child = new GroovyAjaxLink(id) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                def ajaxCallListener = new AjaxCallListener();
                ajaxCallListener.onPrecondition("return confirm('" + text + "');");
                attributes.getAjaxCallListeners().add(ajaxCallListener);
            }
        }
        closure?.call(child)
        child as T
    }

    /**
     * Shortcut for creating new Link
     * Usage example:
     * link('markupId') { link ->
     *     click {*         // click here
     *}*}*
     * param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyLink<M>> T link(parent, String id,
                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                    @ClosureParams(value = FromString, options = 'T')
                                                                            Closure closure = null) {
        build(parent, new GroovyLink<M>(id), closure)
    }

    /**
     * Shortcut for creating new TextField
     * Usage example:
     * text('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyTextField<M>> T text(parent, String id,
                                                                         @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                         @ClosureParams(value = FromString, options = 'T')
                                                                                 Closure closure = null) {
        build(parent, new GroovyTextField(id), closure)
    }

    /**
     * Shortcut for creating new PasswordTextField
     * Usage example:
     * password('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <C extends MarkupContainer, T extends GroovyPasswordTextField> T password(parent, String id,
                                                                                     @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                     @ClosureParams(value = FromString, options = 'T')
                                                                                             Closure closure = null) {
        build(parent, new GroovyPasswordTextField(id), closure)
    }

    /**
     * Shortcut for creating new EmailTextField with email validation behavior
     * Usage example:
     * email('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyEmailField> T email(parent, String id,
                                                                        @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                        @ClosureParams(value = FromString, options = 'T')
                                                                                Closure closure = null) {
        build(parent, new GroovyEmailField(id), closure);
    }

    /**
     * Shortcut for creating new UrlTextField with url validation behavior
     * Usage example:
     * url('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param url
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyUrlField> T url(parent, String id, String url,
                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                    @ClosureParams(value = FromString, options = 'T')
                                                                            Closure closure = null) {
        build(parent, new GroovyUrlField(id, url), closure) as T
    }

    /**
     * Shortcut for creating new TextField with number validation behavior
     * Usage example:
     * number('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyTextField<BigDecimal>> T number(parent, String id,
                                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                    @ClosureParams(value = FromString, options = 'T')
                                                                                            Closure closure = null) {
        build(parent, new GroovyTextField(id, BigDecimal), closure)
    }

    /**
     * Shortcut for creating new DropDownChoice
     * Usage example:
     * dropDown('markupId') { dropDown ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyDropDownChoice<M>> T dropDown(parent, String id,
                                                                                  @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                  @ClosureParams(value = FromString, options = 'T')
                                                                                          Closure closure = null) {
        build(parent, new GroovyDropDownChoice<M>(id), closure)
    }

    /**
     * Shortcut for creating new RadioChoice
     * Usage example:
     * radioChoice('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyRadioChoice<M>> T radioChoice(parent, String id,
                                                                                  @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                  @ClosureParams(value = FromString, options = 'T')
                                                                                          Closure closure = null) {
        build(parent, new GroovyRadioChoice(id), closure)
    }

    /**
     * Shortcut for creating new CheckBoxChoice
     * Usage example:
     * checkBox('markupId') { field ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <T extends GroovyCheckBoxChoice> T checkBox(parent, String id,
                                                       @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                       @ClosureParams(value = FromString, options = 'T')
                                                               Closure closure = null) {
        build(parent, new GroovyCheckBoxChoice(id), closure)
    }

    /**
     * Shortcut for creating new CheckBoxMultipleChoice
     * Usage example:
     * checkChoices('markupId') { choices ->
     *
     *}* @param parent
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyCheckBoxMultipleChoice<M>> T checkChoices(parent, String id,
                                                                                              @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                              @ClosureParams(value = FromString, options = 'T')
                                                                                                      Closure closure = null) {
        build(parent, new GroovyCheckBoxMultipleChoice<M>(id), closure)
    }

    /**
     * Shortcut for creating Wicket choices renderer
     * usually used for entities
     * Usage example:
     * dropDown('markupId') {*  choicesRenderer(id: 'id', value: 'title')
     *}* @param choice
     * @param objectProperty
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    static <A, M extends Serializable, T extends AbstractChoice<M, A>> T choicesRender(
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

    @CompileStatic(TypeCheckingMode.SKIP)
    static <A, M extends Serializable, T extends AbstractChoice<M, A>> T choices(
            T choice, final Map objectProperty) {
        choice = choicesRender(choice, objectProperty)
        choice.setChoices((objectProperty?.from as List)?.toListModel())
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    static <A, M extends Serializable, T extends AbstractChoice<M, A>> T enumChoices(
            final T choice, Class<? extends Enum> enumObject) {
        def list = enumObject.getEnumConstants().toList()
        choice?.setChoiceRenderer(new IChoiceRenderer() {
            @Override
            Object getDisplayValue(Object object) {
                new EnumModel(object).getObject()
            }

            @Override
            String getIdValue(Object object, int index) {
                Enum.valueOf(enumObject, object.toString())
            }
        })
        choice.setChoices(list)
        choice
    }

    /**
     * Shortcut for converting IModel<List> of choices into DropDownChoice
     * Usage example:
     * listModel.toDropDown('markupId') { dropDown ->
     *     choicesRenderer(id: 'id', value: 'title')
     *}* @param choices
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyDropDownChoice<M>> T toDropDown(IModel<? extends List<M>> choices, String id,
                                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                    @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(null, new GroovyDropDownChoice<M>(id, null as IModel, choices), closure)
    }

    /**
     * Shortcut for converting list of choices into DropDownChoice
     * Usage example:
     * list.toDropDown('markupId') { dropDown ->
     *
     *}* @param choices
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyDropDownChoice<M>> T toDropDown(List<M> choices, String id,
                                                                                    @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                                    @ClosureParams(value = FromString, options = 'T') Closure closure = null) {
        build(null, new GroovyDropDownChoice<M>(id, null as IModel, choices), closure)
    }

    /**
     * Shortcut for converting object into IModel and adding it to new Label instance
     * Usage example:
     * serializable.toLabel('markupId') { label ->
     *
     *}* @param o
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyLabel<M>> T toLabel(M o, String id,
                                                                        @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                        @ClosureParams(value = FromString, options = 'T')
                                                                                Closure closure = null) {
        toLabel(loadModel(o), id, closure) as T
    }

    /**
     * Shortcut for adding IModel object to new Label instance
     * Usage example:
     * model.toLabel('markupId') { label ->
     *
     *}* @param model
     * @param id
     * @param closure
     * @return
     */
    static <M extends Serializable, T extends GroovyLabel<M>> T toLabel(IModel<M> model, String id,
                                                                        @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                                        @ClosureParams(value = FromString, options = 'T')
                                                                                Closure closure = null) {
        def label = label(null, id, closure)
        label.setDefaultModel(model)
        label as T
    }

    /**
     * Shortcut for adding AbstractDefaultAjaxBehavior
     * Usage example:
     * ajaxBehavior { AjaxRequestTarget t ->
     *
     *}* @param component
     * @param action
     * @return
     */
    static <A extends AjaxRequestTarget> AbstractDefaultAjaxBehavior ajaxBehavior(component,
                                                                                  @ClosureParams(value = FromString, options = 'A') Closure action) {
        def behavior = new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(AjaxRequestTarget target) {
                action.call(target)
            }
        }
        if (component instanceof Component) {
            component.add behavior
        }
        behavior
    }

    /**
     * Shortcut for adding Ajax event behavior to component
     * Usage example:
     * ajaxEvent('onclick') { AjaxRequestTarget t ->
     *
     *}* @param component
     * @param action
     * @return
     */
    static <A extends AjaxRequestTarget> AjaxEventBehavior ajaxEvent(component, String action,
                                                                     @ClosureParams(value = FromString, options = 'A') Closure closure) {
        def behavior = new AjaxEventBehavior(action) {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                closure.call(target)
            }
        }
        if (component instanceof Component) {
            component.add behavior
        }
        behavior
    }

    /**
     * Shortcut for LoadableDetachableModel
     * Usage example:
     * serializeable.loadModel {*  // return object here
     *}* @param o
     * @return
     */
    static <T extends Serializable> IModel<T> loadModel(final T o) {
        new LoadableDetachableModel(o) {
            @Override
            protected Object load() {
                null
            }
        }
    }

    /**
     * Shortcut for css class attribute appender
     * Usage example:
     * component.css('ui', 'myStyle')
     *
     * @param component
     * @param names
     * @return
     */
    static <T extends Component> T css(T component, String... names) {
        css(component, names.toList())
    }

    /**
     * Shortcut for css class attribute appender
     * Usage example:
     * component.css(['ui', 'myStyle'])
     *
     * @param component
     * @param list
     * @return
     */
    static <T extends Component> T css(T component, List<String> list) {
        component.add(new AttributeAppender('class', Model.of(' ' + list.join(' ')))) as T
    }

    /**
     * Shortcut for css class attribute appender with closure
     * Usage example:
     * component.css {*     ['ui', 'style', someCondition ? 'negative' : 'positive']
     *}* @param component
     * @param closure
     * @return
     */
    static <T extends Component> T css(T component, Closure<List<String>> closure) {
        css(component, closure.call())
    }

    /**
     * Shortcut for CompoundPropertyModel accepts model
     * Usage example:
     * component.compound(imodel)
     *
     * @param component
     * @param model
     * @return
     */
    static <T extends Component> T compoundModel(T component, IModel model) {
        component.setDefaultModel(new CompoundPropertyModel(model))
    }

    /**
     * Shortcut for CompoundPropertyModel accepts Component
     * Usage example:
     *
     * component.compound(anotherComponent)
     * @param component
     * @param modelComponent
     * @return
     */
    static <T extends Component> T compoundModel(T component, Component modelComponent) {
        component.setDefaultModel(new CompoundPropertyModel(modelComponent))
    }

    /**
     * Creates Type safe PropertyModel from component model
     * Usage:
     * def fragment = new UserFragment() // there is a model with User object inside
     * label('name').setModel(fragment.property(User.class) { it.name })
     * label('townTitle').setModel(fragment.property(User.class) { it.town.title })
     *
     * @param component
     * @param clazz
     * @param closure
     * @return
     */
    static <I extends Serializable, T extends Component, S extends Serializable> IModel<I> property(T component, Class<S> clazz,
                                                                                                    @DelegatesTo(value = S, strategy = Closure.DELEGATE_ONLY)
                                                                                                    @ClosureParams(value = FromString, options = 'S')
                                                                                                            Closure<I> closure) {
        def object = new TypeSafeModelBuilder()
        object.with(closure)
        new PropertyModel<I>(component.getDefaultModel(), object.notation)
    }
    /**
     * Creates Type safe PropertyModel
     * Usage:
     * def model = new Model<User>(user)
     * model.property { it.name }* model.property { it.town.title }*
     * @param model
     * @param closure
     * @return
     */
    static <A extends Serializable, I extends Serializable, S extends IModel<A>> IModel<I> property(S model,
                                                                                                    @DelegatesTo(value = A, strategy = Closure.DELEGATE_ONLY)
                                                                                                    @ClosureParams(value = FromString, options = 'A') Closure<I> closure) {
        def object = new TypeSafeModelBuilder()
        object.with(closure)
        new PropertyModel<I>(model, object.notation)
    }

    /**
     * Shortcut for PropertyModel
     * Usage example:
     * component.setModel(imodel.property('title'))
     *
     * @param model
     * @param property
     * @return
     */
    static <T> IModel<T> property(IModel model, String property) {
        new PropertyModel(model, property)
    }

    /**
     * Shortcut for PropertyModel
     * Usage example:
     * component.setModel(serializable.property('title'))
     *
     * @param object
     * @param property
     * @return
     */
    static <T extends Serializable> IModel property(T object, String property) {
        new PropertyModel(object, property)
    }

    /**
     * Shortcut for PropertyModel creating model from component model or not
     * Usage example:
     * component.setModel(anotherComponent.property('title', false))
     * @param object
     * @param property
     * @param fromModel
     * @return
     */
    static <C extends Component, T extends Serializable> IModel<T> property(C object, String property, boolean fromModel = true) {
        fromModel ?
                new PropertyModel(object.getDefaultModel(), property) : new PropertyModel(object, property)
    }

    /**
     * Shortcut for AjaxRequestTarget replacing Component on page
     * Usage example:
     * target.replaceOnPage('markupId', component)
     * @param t
     * @param id
     * @param component
     */
    static void replaceOnPage(AjaxRequestTarget t, String id, Component component) {
        t.add(t.page.get(id)?.replaceWith(component))
    }

    /**
     * Shortcut for AjaxRequestTarget appending jquery javascript code for Component
     * Usage example:
     * target.appendToSelf(component, js)
     * @param t
     * @param component
     * @param js
     */
    static void appendToSelf(AjaxRequestTarget t, Component component, String js) {
        t.appendJavaScript("\$(\'#$component.markupId\').${js}")
    }

    /**
     * Shortcut for AjaxRequestTarget prepending jquery javascript code for Component
     * Usage example:
     * target.prependToSelf(component, js)
     * @param t
     * @param component
     * @param js
     */
    static void prependToSelf(AjaxRequestTarget t, Component component, String js) {
        t.prependJavaScript("\$('#$component.markupId')")
    }

    /**
     * Shortcut for LoadableDetachableModel
     * component.setModel(serializable.toLoadModel())
     * @param object
     * @return
     */
    static <T extends Serializable> IModel<T> toLoadModel(final T object) {
        loadModel(object)
    }

    static <M extends Serializable, T extends Collection<M>> IModel<List<M>> toListModel(final T list) {
        new ListModel(list.toList())
    }

    /**
     * Shortcut for LoadableDetachableModel
     * component.setModel {*      //closure here
     *}.toLoadModel()
     * @param closure
     * @return
     */
    static IModel toLoadModel(final Closure closure) {
        loadModel(null) {
            closure.call()
        }
    }

    /**
     * Shortcut for simple model
     * @param object
     * @return
     */
    static <T extends Serializable> IModel<T> toModel(T object) {
        Model.of(object)
    }

    /**
     * Shortcut for Component.getString
     * component.setModel(stringModel('key', model))
     * @param component
     * @param key
     * @param model
     * @return
     */
    static <C extends Component> IModel stringModel(C component, String key, IModel model = null) {
        loadModel(component.getString(key, model, key))
    }

    /**
     * Method for component build automation
     * @param parent
     * @param child
     * @param closure
     * @return
     */
    private static <T extends Component> T build(parent, T child,
                                                 @DelegatesTo(value = T, strategy = Closure.DELEGATE_FIRST)
                                                 @ClosureParams(value = FromString, options = 'T')
                                                         Closure closure = null) {
        def cloned = closure?.rehydrate(child, closure.owner, closure.thisObject)
        cloned?.resolveStrategy = Closure.DELEGATE_FIRST
        cloned?.call(child)
        if (parent instanceof MarkupContainer) {
            parent.addOrReplace(child) as T
        } else if (parent instanceof Closure && parent.delegate instanceof MarkupContainer) {
            (parent.delegate as MarkupContainer).addOrReplace child
        }
        child
    }

    /**
     * Overloaded operator ">>" for replacing one Component with another
     * @param t
     * @param component
     * @param js
     */
    static <C extends Component, T extends Component> T rightShift(C container, T another) {
        container?.replaceWith another
    }

    /**
     * Overloaded operator "+" for adding child component to parent
     * @param parent
     * @param child
     * @return
     */
    static <T extends MarkupContainer, C extends Component> T plus(T parent, C child) {
        parent?.add child
    }

    /**
     * Overloaded operator "+" for adding behavior to component
     * @param parent
     * @param behavior
     * @return
     */
    static <T extends MarkupContainer> T plus(T parent, Behavior behavior) {
        parent?.add(behavior) as T
    }

    /**
     * Overloaded operator "-" for removing child component from parent
     * @param parent
     * @param child
     * @return
     */
    static <T extends MarkupContainer, C extends Component> T minus(T parent, C child) {
        parent?.remove child
    }

    /**
     * Overloaded operator "<<" for addOrReplace component method
     * @param parent
     * @param child
     * @return
     */
    static <T extends MarkupContainer, C extends Component> T leftShift(T parent, C child) {
        parent?.addOrReplace child
    }

    /**
     * Method that find all Closures in map
     * @param map
     * @return
     */
    static Map<String, Closure> override(Map<String, Object> map) {
        map?.findAll { it.value instanceof Closure }
    }
}


