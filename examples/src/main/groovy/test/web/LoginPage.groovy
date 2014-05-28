package test.web

import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.CompoundPropertyModel
import wicket.groovy.WicketDSL
import wicket.groovy.WicketFormDSL

class LoginPage extends TemplatePage {
    String juname
    String jup

    @Override
    protected void onInitialize() {
        super.onInitialize()
        use(WicketDSL, WicketFormDSL) {
            def form = statelessForm('login', new CompoundPropertyModel(this), [submit: {
                this.juname == this.jup ? info('Login Successful') : error('Login failed')
            }])
            form.text('juname')
            form.password('jup')
            form + new FeedbackPanel('feedback')
        }
    }
}
