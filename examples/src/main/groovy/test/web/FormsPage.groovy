package test.web

import org.apache.wicket.model.CompoundPropertyModel
import wicket.groovy.GroovyDSL

class FormsPage extends TemplatePage {
    private Integer counter = 0

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(GroovyDSL) {
            def simpleForm = form 'simpleForm', new CompoundPropertyModel(this), [submit: { this.counter++ }]
            simpleForm.label('counter')
        }
    }
}
