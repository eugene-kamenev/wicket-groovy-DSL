package test.web

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.panel.FeedbackPanel

class LoginPage extends TemplatePage {
    String juname
    String jup

    @Override
    protected void onInitialize() {
        super.onInitialize()
        statelessForm('login') {
            it + new FeedbackPanel('feedback')
            text('juname').model(this.property('juname', false))
            password('jup').model(this.property('jup', false))
            submit {
                this.juname == this.jup ? info('Login Successful') : error('Login failed');
            }
        }
    }
}
