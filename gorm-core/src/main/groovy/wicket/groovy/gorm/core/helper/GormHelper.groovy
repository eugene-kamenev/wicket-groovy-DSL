package wicket.groovy.gorm.core.helper

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.validation.ConstrainedProperty

class GormHelper {
    static final def domainClassProperties = new HashMap<Class, Map<String, ConstrainedProperty>>()

    static Map<String, ConstrainedProperty> getConstraints(Class clazz) {
        def properties = domainClassProperties.get(clazz)
        if (!properties) {
            properties = new DefaultGrailsDomainClass(clazz).constrainedProperties
            domainClassProperties.put(clazz, properties)
        }
        return properties
    }
}
