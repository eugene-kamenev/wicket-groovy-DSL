package wicket.groovy.core.model

import groovy.transform.CompileStatic
import org.apache.wicket.Application
import org.apache.wicket.model.LoadableDetachableModel

@CompileStatic
class EnumModel extends LoadableDetachableModel<String> {
    Enum enumObject

    EnumModel(Object enumObject) {
        this.enumObject = enumObject as Enum
    }

    @Override
    protected String load() {
        Application.get().getResourceSettings().getLocalizer().getString("${enumObject.class.simpleName}.${enumObject.toString()}", null)
    }
}
