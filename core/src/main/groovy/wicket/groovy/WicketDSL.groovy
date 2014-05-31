package wicket.groovy

import groovy.transform.CompileStatic
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.Page
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.markup.html.panel.Fragment
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.request.resource.ResourceReference
import wicket.groovy.core.OperatorsOverload
import wicket.groovy.core.components.ajax.GroovyAjaxLink
import wicket.groovy.core.components.basic.*
import wicket.groovy.core.components.form.GroovyForm
import wicket.groovy.core.components.form.GroovyStatelessForm

@CompileStatic
class WicketDSL extends OperatorsOverload {

    static <T> WebMarkupContainer div(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyWebMarkupContainer(id, params?.model as IModel, override(params))
        parent?.add child
        closure?.call(child)
        child
    }

    static <T> Image image(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def image
        if (params?.resource) {
            image = new GroovyImage<T>(id, params?.resource as ResourceReference, override(params))
        } else if (params?.image) {
            image = new GroovyImage<T>(id, params?.image as IResource, override(params))
        } else if (params?.params) {
            if (params?.params instanceof Map) {
                def parameters = new PageParameters();
                (params?.params as Map<String, Object>)?.each { k, v ->
                    parameters.add(k, v)
                }
                params?.params = parameters
            }
            image = new GroovyImage<T>(id, params?.resource as ResourceReference, params?.params as PageParameters, override(params))
        } else if (params?.model) {
            image = new GroovyImage<T>(id, params?.model as IModel, override(params))
        } else {
            image = new GroovyImage<T>(id, override(params))
        }
        closure?.call(image)
        image
    }

    static <T> Fragment fragment(MarkupContainer parent, String id, String markupId, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyFragment(id, markupId, params?.provider as MarkupContainer ?: parent, params?.model as IModel<T>, override(params))
        parent?.add child
        closure?.call(child)
        child
    }

    static <T> Fragment fragment(MarkupContainer parent, String id, String markupId, Closure closure) {
        fragment(parent, id, markupId, null, closure)
    }

    static <T> ListView<T> listView(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyListView(id, params?.model as IModel<List<T>>, override(params), closure)
        parent?.add child
        child
    }

    static <T> ListView<T> listView(List<T> list, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyListView<T>(id, list, override(params), closure)
        child
    }

    static <T> ListView<T> listView(List<T> list, String id, Closure closure) {
        def child = new GroovyListView<T>(id, list, null, closure)
        child
    }

    static <T> LoadableDetachableModel<T> loadModel(parent, Closure closure) {
        def model = new LoadableDetachableModel() {
            @Override
            protected Object load() {
                closure.call()
            }
        }
        model
    }

    static <T> StatelessForm<T> statelessForm(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def form
        if (params?.model) {
            form = new GroovyStatelessForm<T>(id, params?.model as IModel<T>, override(params))
        } else {
            form = new GroovyStatelessForm<T>(id, override(params))
        }
        parent?.add form
        closure?.call(form)
        form
    }

    static <T> Form<T> form(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def form
        if (params?.model) {
            form = new GroovyForm<T>(id, params?.model as IModel<T>, override(params))
        } else {
            form = new GroovyForm<T>(id, override(params))
        }
        parent?.add form
        closure?.call(form)
        form
    }

    static BookmarkablePageLink bookmarkLink(MarkupContainer parent, String id, Class<? extends Page> target, Map<String, Object> params = null, Closure closure = null) {
        if (params?.params instanceof Map) {
            def parameters = new PageParameters();
            (params?.params as Map<String, Object>)?.each { k, v ->
                parameters.add(k, v)
            }
            params?.params = parameters
        }
        def link = new GroovyBookmarkablePageLink(id, target, params?.params as PageParameters, override(params))
        parent?.add link
        closure?.call(link)
        link
    }

    static <T> Label label(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child
        if (params?.model) {
            child = new GroovyLabel(id, params?.model as IModel<T>, override(params))
        } else if ((params?.value instanceof String || params?.value instanceof Serializable) && !params?.value instanceof IModel) {
            child = new GroovyLabel(id, params?.value as String, override(params));
        } else {
            child = new GroovyLabel(id)
        }
        parent?.add child
        closure?.call(child)
        child
    }

    static <T> AjaxLink ajaxLink(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyAjaxLink(id, params?.model as IModel<T>, override(params))
        parent?.add child
        closure?.call(child)
        child
    }

    static <T> Link<T> link(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def child = new GroovyLink(id, params?.model as IModel<T>, override(params))
        parent?.add child
        closure?.call(child)
        child
    }

    static Component rightShift(MarkupContainer container, Component another) {
        container?.replaceWith another
    }

    static Component plus(MarkupContainer parent, Component child) {
        parent?.add child
    }

    static Component minus(MarkupContainer parent, Component child) {
        parent?.remove child
    }

    static Component leftShift(MarkupContainer parent, Component child) {
        parent?.addOrReplace child
    }

    static Map<String, Closure> override(Map<String, Object> map) {
        map?.findAll { it.value instanceof Closure }
    }
}
