package test

import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import org.apache.wicket.Page
import org.apache.wicket.RuntimeConfigurationType
import org.apache.wicket.protocol.http.WebApplication
import org.h2.Driver
import org.springframework.context.ApplicationContext
import org.springframework.jdbc.datasource.DriverManagerDataSource
import test.domain.Department
import test.domain.Person
import test.web.HomePage

class GormApplication extends WebApplication {
    @Override
    protected void init() {
        super.init()
        initGorm()
        getResourceSettings().setThrowExceptionOnMissingResource(false)
        getResourceSettings().setUseDefaultOnMissingResource(true)
        def departments = [] as List<Department>
        10.times { d ->
            def department = new Department(title: "Department ${d}")
            10.times { p ->
                department.persons.add(new Person(name: "Person ${department.title}${p}",
                        department: department, email: "person_${d}_${p}_email@gmail.com", password: '123456'))
            }
            departments.add(department)
        }
        departments*.save(insert: true)
    }

    @Override
    RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEVELOPMENT
    }

    @Override
    Class<? extends Page> getHomePage() {
        HomePage
    }

    static ApplicationContext initGorm() {
        def init = new HibernateDatastoreSpringInitializer('test.domain', 'wicket.groovy.gorm.core.domain')
        def dataSource = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", 'sa', '')
        dataSource.setDriverClassName(Driver.name)
        init.setConfiguration(new Properties(['hibernate.hbm2ddl.auto': 'create', 'hibernate.flushMode': 'manual', 'hibernate.showSql': 'true']))
        init.configureForDataSource(dataSource)
    }
}
