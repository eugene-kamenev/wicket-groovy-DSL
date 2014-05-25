package test.web
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.CompoundPropertyModel
import wicket.groovy.WicketDSL

class FormsPage extends TemplatePage {
    private Integer sum = 0
    private Integer first
    private Integer second

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL) {
            def simpleForm = form 'simpleForm', new CompoundPropertyModel(this), [submit: { this.sum = this.first + this.second }]
            simpleForm << new TextField('first')
            simpleForm << new TextField('second')
            simpleForm.label('sum')
        }
    }
}
