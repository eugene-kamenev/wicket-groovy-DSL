package test
import de.agilecoders.wicket.webjars.WicketWebjars
import groovy.transform.CompileStatic
import org.apache.wicket.protocol.http.WebApplication
import test.web.HomePage

@CompileStatic
class TestApplication extends WebApplication {

    Class homePage = HomePage

    @Override
    protected void init() {
        super.init()
        getMarkupSettings().setStripWicketTags(true)
        WicketWebjars.install(this)
    }
}
