package wicket.groovy.gems
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString
import org.apache.wicket.Application
import org.apache.wicket.Component
import org.apache.wicket.markup.head.*
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.Model
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
    List<T> arrayList

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
    Map fetchParams

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
    GroovyDataTable<T> cell(String id,
                            @ClosureParams(value = FromString, options = 'wicket.groovy.gems.Column')
                            @DelegatesTo(value = Column, strategy = Closure.DELEGATE_FIRST)
                                    Closure closure = null) {
        def column = new Column(id: id)
        closure?.delegate = column
        closure?.call(column)
        columns.add(column)
        this
    }

    /**
     * In many cases you will have probably one
     * default table in a Panel, when you will include this table
     * with another component, you can include or rebuild columns
     *
     * @param closure
     */
    void setInclude(@DelegatesTo(GroovyDataTable) Closure closure) {
        def clone = closure?.rehydrate(this, closure?.owner, closure?.thisObject)
        clone?.call(this)
    }

    /**
     * In many cases you will have probably one
     * default table in a Panel, when you will include this table
     * with another component, you can exclude columns
     *
     * @param columns
     */
    void setExclude(String... columns) {
        if (columns?.size() > 0) {
            this.columns = this.columns.findAll { !(it.id in columns) }.toList()
        }
    }

    /**
     * You can reorder columns in table
     * @param fields
     */
    void reorder(String[] fields) {
        if (fields) {
            def fieldList = new HashSet<>(fields.toList()) as List
            def columnIds = this.columns.collect { it.id }
            def sortedColumnIds = (columnIds.intersect(fieldList).sort {
                fieldList.indexOf(it)
            } + (columnIds - fieldList).sort())
            def sortedColumns = []
            sortedColumnIds.each { id ->
                def column = this.columns.find { it.id == id }
                column ? sortedColumns << column : null
            }
            this.columns = sortedColumns
        }
    }

    /**
     * Shortcut for default cells
     * @param ids
     */
    def cells(String... ids) {
        ids.each { String id ->
            cell(id)
        }
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
    void list(Closure<List<T>> list) {
        this.listClosure = list as Closure<List<T>>
    }

    /**
     * List setter
     * @param list
     * @return
     */
    def list(List<T> list) {
        this.arrayList = list
        this
    }

    GroovyDataTable<T> build() {
        headersKey ?: ''
        listView('headers', columns) {
            it.label('cell').model(this.stringModel("$headersKey.${(it.modelObject as Column).id}"))
        }
        arrayList = arrayList ?: listClosure?.call(getPageId())
        arrayList = arrayList ?: []
        this.content = (div('content') {
            this.rows = listView('rows', this.arrayList) {
                rowBuild(it)
            }
            outputMarkupId = true
        })
        this.rows.outputMarkupId = true
        this
    }

    ListItem rowBuild(ListItem item) {
        item.outputMarkupId = true
        item.setModel(Model.of(item.getModelObject() as Serializable)) // you should override this to loadable detachable model
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
            if (column.modify) {
                column.modify.call(component)
            }
            component?.setEscapeModelStrings(false)
        }
        rows + (item + view)
        item
    }

    @Override
    protected void onDetach() {
        super.onDetach()
        this.arrayList = null
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
        closure?.delegate = table
        closure?.call(table)
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

    int height = 450

    @Override
    GroovyDataTable build() {
        super.build()
        def script = """\$('#${this.markupId}').dataTable({
        "scrollCollapse": true,
        "sScrollY": $height,
        "searching": true,
        "paging": false,
        "stripeClasses": [],
        "deferRender": true,
        "responsive": true,
        "destroy" : true
        });"""
        this + new InfiniteScrollBehavior(element: 'tr', list: this.listClosure, headerScript: script)
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
        def clone = closure.rehydrate(table, closure?.owner, closure?.thisObject)
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone(table)
        table.build() as InfiniteScrollDataTable
    }
}

/**
 * Class Column for GroovyDataTable
 */
@CompileStatic
class Column<T> implements Serializable {
    String id
    Closure<Component> item
    Closure modify
    int order

    Column<T> item(@DelegatesTo(value = ListItem, strategy = Closure.DELEGATE_FIRST)
                   @ClosureParams(value = FromString, options = ['java.lang.String,org.apache.wicket.model.IModel'])
                           Closure<Component> closure) {
        this.item = closure
        this
    }

    Column<T> modify(@ClosureParams(value = FromString, options = 'org.apache.wicket.Component') Closure closure) {
        this.modify = closure
        this
    }

    Column<T> order(int order) {
        this.order = order
        this
    }
}
