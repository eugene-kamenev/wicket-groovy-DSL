package test.web

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink

@InheritConstructors
@CompileStatic
class GemsPage extends TemplatePage {
    static final Closure alert = { AjaxRequestTarget target, AjaxLink<Person> link ->
        target.appendJavaScript("alert('$link.model.object.name " +
                "$link.model.object.surname selected')")
    }

    @Override
    protected void onInitialize() {
        super.onInitialize()
        infiniteTable('table') {
            cell('id')
            cell('name')
            cell('surname')
            cell('action') {
                item { id, imodel ->
                    ajaxLink(id) {
                        body = 'Click me!'.toLoadModel()
                        model = imodel
                        click { AjaxRequestTarget t, AjaxLink link ->
                            alert(t, link)
                        }
                    }
                }
                modify {
                    css {
                        ['btn', 'btn-primary']
                    }
                }
            }
            list { Long offset ->
                Person.generate(offset)
            }
        }
    }

    static class Person implements Serializable {
        Long id
        String name
        String surname

        static List<Person> generate(Long offset) {
            def list = []
            40.times {
                def id = (offset++) * it
                list << new Person(id: id, name: "Gomer $id", surname: "Simpson $id")
            }
            list
        }
    }
}
