package wicket.groovy.gems

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.apache.wicket.Application
import org.apache.wicket.Component
import org.apache.wicket.markup.head.CssHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.head.JavaScriptHeaderItem
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem
import org.apache.wicket.markup.head.PriorityHeaderItem
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.request.resource.CssResourceReference
import org.apache.wicket.request.resource.ResourceReference

/**
 * Class for building simple Groooovy DataTable
 * @author @eugenekamenev
 */
@InheritConstructors
@CompileStatic
class GroovyDataTable<T> extends GenericPanel<T> {

    /**
     * Column list
     */
    List<Column> columns = []

    /**
     * List Closure
     */
    Closure<List<T>> listClosure

    /**
     * List of entities
     */
    List<T> list

    /**
     * ListView that rows are attached at
     */
    ListView<T> rows

    /**
     * Content container for ListView
     */
    WebMarkupContainer content

    /**
     * Optional
     * Entity retrieve params (see grails docs for method Entity.findById(id, params))
     */
    Map entityParams

    /**
     * StringResourceKey for headers
     */
    String headersKey

    /**
     * id of the page for InfiniteScroll
     */
    Long pageId = 0

    /**
     * Column builder method
     * @param id
     * @param closure
     * @return GroovyDataTable
     */
    def cell(String id,
             @ClosureParams(value = FromString, options = 'Column')
             @DelegatesTo(value = Column, strategy = Closure.DELEGATE_FIRST)
                     Closure closure = null) {
        def column = new Column(id: id)
        closure?.delegate = column
        closure?.call(column)
        columns.add(column)
        this
    }
    /**
     * Headers StringResource key setter
     * @param headersKey
     * @return
     */
    def headersKey(String headersKey) {
        this.headersKey = headersKey
    }

    /**
     * Closure list setter
     * @param list
     * @return GroovyDataTable
     */
    def list(@ClosureParams(value = FromString, options = 'java.lang.Long')Closure<List<T>> list) {
        this.listClosure = list
    }

    /**
     * List setter
     * @param list
     * @return
     */
    def list(List<T> list) {
        this.list = list
        this
    }

    GroovyDataTable<T> build() {
        headersKey ?: ''
        listView('headers', columns) {
            it.label('cell').model(this.stringModel("$headersKey.${(it.modelObject as Column).id}"))
        }
        list = list ?: listClosure?.call(getPageId())
        list = list ?: []
        div('content') {
            this.content = it as WebMarkupContainer
            this.rows = listView('rows', this.list as List) {
                rowBuild(it)
            } as ListView<T>
            this.rows.outputMarkupId = true
            setOutputMarkupId(true)
        }
        this
    }

    ListItem rowBuild(ListItem item) {
        item.outputMarkupId = true
        def view = new RepeatingView('cell')
        view.setOutputMarkupId(true)
        this.columns.eachWithIndex { column, i ->
            column.item?.delegate = item
            Component component
            if (column.item) {
                component = (column.item.call(view.newChildId(), item.model) as Component)
                view + component
            } else {
                component = (view.label(view.newChildId()).model(item.property(column.id, true)) as Component)
            }
            column.cssClasses ? component.css(column.cssClasses.call(item.model) as List<String>) : null
        }
        item + view
        rows + item
        item
    }

    /**
     * GroovyDataTable builder method
     *
     * @param id Wicket markup id
     * @param closure Closure delegate
     * @return GroovyDataTable
     */
    static <T> GroovyDataTable<T> create(String id,
                                         @ClosureParams(value = FromString, options = 'GroovyDataTable')
                                         @DelegatesTo(value = GroovyDataTable, strategy = Closure.DELEGATE_FIRST)
                                                 Closure closure) {
        def table = new GroovyDataTable<T>(id)
        table.outputMarkupId = true
        closure.delegate = table
        closure(table)
        table.build()
    }

    Long getPageId() {
        pageId++
    }
}
/**
 * GroovyDataTable with infinite scrolling behavior
 */
@CompileStatic
@InheritConstructors
class InfiniteScrollDataTable extends GroovyDataTable implements InfiniteScroll {
    static
    final ResourceReference datatableJS = new WebjarsJavaScriptResourceReference('datatables/current/js/jquery.dataTables.min.js')
    static
    final ResourceReference datatableCss = new CssResourceReference(GroovyDataTable, 'GroovyDataTable.css')

    int height = 400

    @Override
    GroovyDataTable build() {
        super.build()
        def script = """\$('#${this.markupId}').dataTable({
        "scrollCollapse": true,
        "sScrollY": $height,
        "searching": true,
        "paging":         false,
        "stripeClasses": [],
        "deferRender": true,
        "responsive": true
        });"""
        this + new InfiniteScrollBehavior(element: 'tr',
                list: this.listClosure,
                headerScript: script)
        this
    }

    @Override
    void renderHead(IHeaderResponse response) {
        super.renderHead(response)
        response.render(new PriorityHeaderItem(JavaScriptReferenceHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference())));
        response.render(JavaScriptHeaderItem.forReference(datatableJS))
        response.render(CssHeaderItem.forReference(datatableCss))
    }

    def height(int height) {
        this.height = height
        this
    }

    /**
     * GroovyDataTable builder method
     *
     * @param id Wicket markup id
     * @param closure Closure delegate
     * @return GroovyDataTable
     */
    static <T> InfiniteScrollDataTable create(String id,
                                              @ClosureParams(value = FromString, options = 'InfiniteScrollDataTable')
                                              @DelegatesTo(value = InfiniteScrollDataTable, strategy = Closure.DELEGATE_FIRST)
                                                      Closure closure) {
        def table = new InfiniteScrollDataTable(id)
        table.outputMarkupId = true
        closure.delegate = table
        closure(table)
        table.build() as InfiniteScrollDataTable
    }
}

/**
 * Class Column for GroovyDataTable
 */
@CompileStatic
class Column<T> implements Serializable {
    String id
    Closure<List<String>> cssClasses
    Closure<Component> item
    boolean visible

    def item(@DelegatesTo(value = ListItem, strategy = Closure.DELEGATE_FIRST)
             @ClosureParams(value = FromString, options = ['java.lang.String,org.apache.wicket.model.IModel'])
                     Closure<Component> closure) {
        this.item = closure
        this
    }

    def css(@ClosureParams(value = FromString, options = ['org.apache.wicket.model.IModel'])
                    Closure<List<String>> cssClasses) {
        this.cssClasses = cssClasses
    }
}
