package test.web

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink

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
                        click { AjaxRequestTarget t, AjaxLink<Person> link ->
                            alert(t, link)
                        }
                    }
                }
                it.css {
                    ['btn', 'btn-primary']
                }
            }
            list { offset ->
                Person.generate(offset)
            }
        }
    }

    static class Person implements Serializable {
        Long id
        String name
        String surname

        static List<Person> generate(long offset) {
            def list = []
            40.times {
                def id = (offset + 1) * it
                list << new Person(id: id, name: "Gomer $id", surname: "Simpson $id")
            }
            list
        }
    }
}
