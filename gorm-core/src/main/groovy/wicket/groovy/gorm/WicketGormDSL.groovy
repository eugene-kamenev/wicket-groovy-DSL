package wicket.groovy.gorm

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.markup.repeater.data.DataView
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.IModel
import org.apache.wicket.model.PropertyModel
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.springframework.validation.FieldError
import wicket.groovy.WicketFormDSL
import wicket.groovy.gorm.core.components.GormDataProvider
import wicket.groovy.gorm.core.components.GormDataView
import wicket.groovy.gorm.core.domain.GormEntity
import wicket.groovy.gorm.core.helper.GormHelper

@CompileStatic
class WicketGormDSL extends WicketFormDSL {
    @CompileStatic(TypeCheckingMode.SKIP)
    static <T extends GormEntity> Form<T> entityForm(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        params.validate = { Form<GormEntity> form ->
            def object = form.modelObject
            if (!object.validate(params?.fields as List)) {
                (object.errors.allErrors as List<FieldError>).each { FieldError error ->
                    form.error(form.getString("${error.objectName}.${error.field}.${error.code}", form.model))
                }
            }
        }
        params.model = new CompoundPropertyModel(params.model)

        def form = form(parent, id, params) { Form<T> form ->
            def fields = params?.fields as List
            fields?.each {
                def clazz = form.modelObject.class
                def property = GormHelper.getConstraints(clazz).get(it)
                field(property, form)
            }

        }
        closure?.call form
        form
    }

    static <T extends GormEntity> DataView<T> entityView(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure) {
        def dataView = new GormDataView<T>(id, new GormDataProvider<T>(params), override(params), closure)
        parent?.add dataView
        dataView
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    static <T extends GormEntity> DataView<T> beanDataView(MarkupContainer parent, String id, Map<String, Object> params = null, Closure closure = null) {
        def dataView = entityView(parent, id, params, {
            Item<T> item ->
                def clazz = item.modelObject.class
                item.model = new CompoundPropertyModel<T>(item.modelObject)
                def fields = params?.fields as List<String>
                fields?.each {
                    if (it.contains('.')) {
                        // add functionality for dotted wicket property style
                    } else {
                        def property = GormHelper.getConstraints(clazz).get(it)
                        view(property, item, item.model)
                    }
                }
                closure?.delegate = item
                use(WicketGormDSL) { closure?.call item }
        })
        dataView
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static Component view(ConstrainedProperty property, MarkupContainer parent, IModel model) {
        if (property) {
            def view = property?.getMetaConstraintValue('view') as Map
            switch (property?.propertyType) {
                case Set: listView(parent, property?.propertyName, [model: new PropertyModel(model, property?.propertyName)]) {
                    ListItem item -> item.add specificView(view?.get(property?.propertyName) as Map, property?.propertyName, property.propertyType, item.model)
                };
                    break
                case GormEntity: parent?.add(specificView(view?.get(property?.propertyName) as Map, property?.propertyName, property.propertyType, model));
                    break
                default: label(parent, property?.propertyName);
                    break
            }
        }
    }

    private static Component specificView(Map view, String name, Class clazz, IModel model) {
        def markupContainer = new WebMarkupContainer(name)
        markupContainer.setDefaultModel(new CompoundPropertyModel(new PropertyModel(model, name)))
        if (view?.fields) {
            (view?.fields as List<String>)?.each {
                this.view(GormHelper.getConstraints(clazz).get(it),
                        markupContainer, markupContainer.getDefaultModel())
            }
        }
        markupContainer
    }

    private static field(ConstrainedProperty property, Form parent) {
        def form = property?.getMetaConstraintValue('form') as Map
        switch (property?.propertyType) {
            case String: if (property?.password) {
                password(parent, property?.propertyName)
            } else if (property?.email) {
                email(parent, property?.propertyName)
            } else {
                text(parent, property?.propertyName);
            }; break
            case Integer: number(parent, property?.propertyName);
                break;
            case Set:
                specificField(form, property?.propertyName, 'checkChoices', parent)
                break;
            case GormEntity:
                specificField(form, property?.propertyName, 'dropDown', parent)
                break
            case Boolean:
                specificField(form, property?.propertyName, 'checkBox', parent)
                break;
        }
    }


    private static Component specificField(Map formMapping, String name, String defaultComponent, Form parent) {
        def component = formMapping?.type
        if (!component) component = defaultComponent
        switch (component) {
            case 'dropDown': dropDown(parent, name, [choices: (formMapping?.choices as Closure)?.call(), render: formMapping?.render]); break
            case 'checkChoices': checkChoices(parent, name, [choices: (formMapping?.choices as Closure)?.call(), render: formMapping?.render]); break
            case 'radioChoice': radioChoice(parent, name, [choices: (formMapping?.choices as Closure)?.call(), render: formMapping?.render]); break
            case 'checkBox': checkBox(parent, name); break
        }
    }
}
