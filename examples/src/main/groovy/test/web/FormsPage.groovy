package test.web

import org.apache.wicket.model.CompoundPropertyModel
import wicket.groovy.WicketDSL
import wicket.groovy.WicketFormDSL

class FormsPage extends TemplatePage {
    private Integer sum = 0
    private Integer first
    private Integer second

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL, WicketFormDSL) {
            def simpleForm = form 'simpleForm', [model: new CompoundPropertyModel(this), submit: {
                this.sum = this.first + this.second
            }]
            simpleForm.text('first')
            simpleForm.text('second')
            simpleForm.label('sum')
        }
    }
}
