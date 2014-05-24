package test

import test.web.HomePage
import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication

class TestApplication extends WebApplication {
    @Override
    protected void init() {
        super.init()
        getMarkupSettings().setStripWicketTags(true)
    }

    @Override
    Class<? extends Page> getHomePage() {
        return HomePage
    }
}
