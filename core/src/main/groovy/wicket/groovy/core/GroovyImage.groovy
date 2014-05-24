package wicket.groovy.core

import groovy.transform.CompileStatic
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.request.resource.ResourceReference
import wicket.groovy.core.traits.WicketComponentTrait

@CompileStatic
class GroovyImage<T> extends Image implements WicketComponentTrait {

    GroovyImage(String id, Map<String, Closure> override = null) {
        super(id)
        this.override = override
    }

    GroovyImage(String id, ResourceReference resourceReference, Map<String, Closure> override = null) {
        super(id, resourceReference)
        this.override = override
    }

    GroovyImage(String id, ResourceReference resourceReference, PageParameters resourceParameters, Map<String, Closure> override = null) {
        super(id, resourceReference, resourceParameters)
        this.override = override
    }

    GroovyImage(String id, IResource imageResource, Map<String, Closure> override = null) {
        super(id, imageResource)
        this.override = override
    }

    GroovyImage(String id, IModel<T> model, Map<String, Closure> override = null) {
        super(id, model)
        this.override = override
    }

    GroovyImage(String id, String string, Map<String, Closure> override = null) {
        super(id, string)
        this.override = override
    }
}