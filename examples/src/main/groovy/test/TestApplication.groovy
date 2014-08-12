package test

import de.agilecoders.wicket.webjars.WicketWebjars
import test.web.HomePage
import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication

class TestApplication extends WebApplication {
    @Override
    protected void init() {
        super.init()
        getMarkupSettings().setStripWicketTags(true)
        WicketWebjars.install(this)
    }

    @Override
    Class<? extends Page> getHomePage() {
        HomePage
    }
}
