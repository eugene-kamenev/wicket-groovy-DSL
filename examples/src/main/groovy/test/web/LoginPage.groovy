package test.web

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.apache.wicket.markup.html.panel.FeedbackPanel

@CompileStatic
@InheritConstructors
class LoginPage extends TemplatePage {
    String juname
    String jup

    @Override
    protected void onInitialize() {
        super.onInitialize()
        form('login') { form ->
            compoundModel(this)
            form + new FeedbackPanel('feedback')
            text('juname')
            password('jup')
            submit {
                this.juname == this.jup ? info('Login Successful') : error('Login failed')
            }
        }
    }
}
