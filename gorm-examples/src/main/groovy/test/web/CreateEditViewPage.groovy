package test.web

import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

abstract class CreateEditViewPage extends TemplatePage {

    CreateEditViewPage() {

    }

    CreateEditViewPage(PageParameters parameters) {
        super(parameters)
    }

    CreateEditViewPage(IModel<?> model) {
        super(model)
    }

    abstract void view()

    abstract void edit(Long id)
}
