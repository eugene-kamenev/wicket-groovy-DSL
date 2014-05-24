package wicket.groovy

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.Page
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import wicket.groovy.core.*

class GroovyDSL {

    static MarkupContainer div(Component parent, String id, Closure closure = null) {
        div(parent, id, null, closure)
    }

    static MarkupContainer div(Component parent, String id, Map<String, Closure> overrides, Closure closure = null) {
        div(parent, id, null, overrides, closure)
    }

    static <T> MarkupContainer div(Component parent, String id, IModel<T> model, Map<String, Closure> overrides = null, Closure closure = null) {
        def panel = new GroovyWebMarkupContainer(id, model, overrides)
        parent?.add panel
        closure?.call(panel)
        panel
    }


    static BookmarkablePageLink bookmarkLink(Component parent, String id, Class<? extends Page> target, Closure closure = null) {
        def link = new BookmarkablePageLink(id, target)
        parent?.add link
        closure?.call(link)
        link
    }

    static <T> Label label(Component parent, String id, IModel<T> model = null, Closure closure = null) {
        def child = new Label<T>(id, model)
        parent?.add child
        closure?.call(child)
        child
    }

    static <T> AjaxLink ajaxLink(Component parent, String id, IModel<T> model, Map<String, Closure> overrides = null, Closure closure = null) {
        def child = new GroovyAjaxLink(id, model, overrides)
        parent?.add child
        closure?.call(child)
        child
    }

    static <T> AjaxLink ajaxLink(Component parent, String id, Map<String, Closure> overrides = null, Closure closure = null) {
        ajaxLink(parent, id, null, overrides, closure)
    }

    static <T> Form<T> form(Component parent, String id, IModel<T> model = null, Map<String, Closure> overrides = null, Closure closure = null) {
        def form = new GroovyForm<T>(id, model, overrides)
        parent?.add form
        closure?.call(form)
        form
    }

    static <T> StatelessForm<T> statelessForm(Component parent, String id, IModel<T> model = null, Map<String, Closure> overrides = null, Closure closure = null) {
        def form = new GroovyStatelessForm<T>(id, model, overrides)
        parent?.add form
        closure?.call(form)
        form
    }

    static <T> ListView<T> listView(Component parent, String id, IModel<? extends List<T>> model = null, Map<String, Closure> overrides = null, Closure closure = null) {
        def listView = new GroovyListView<T>(id, model, overrides, closure)
        parent?.add listView
        listView
    }

    static <T> ListView<T> listView(List<T> list, String id, Map<String, Closure> overrides = null, Closure closure) {
        def listView = new GroovyListView<T>(id, list, overrides, closure)
        listView
    }

    static <T> Image image(Component parent, String id, IModel<T> model = null, Map<String, Closure> overrides = null, Closure closure = null) {
        def image = new GroovyImage<T>(id, model, overrides)
        parent?.add image
        closure?.call(image)
        image
    }

    static leftShift(Component parent, Component... child) {
        parent?.add child
    }
}