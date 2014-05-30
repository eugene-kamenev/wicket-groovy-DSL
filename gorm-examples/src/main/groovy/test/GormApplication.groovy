package test
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import org.apache.wicket.Page
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
        def departments = [new Department(title: 'First Department'), new Department(title: 'Second Department')]
        departments*.save(insert: true)
        def persons1 = [new Person(name: 'Person 1'), new Person(name: 'Person 2'), new Person(name: 'Person 3')]
        persons1*.department = departments[0]
        persons1*.save(insert: true)

        def persons2 = [new Person(name: 'Person 4'), new Person(name: 'Person 5'),
                        new Person(name: 'Person 6'), new Person(name: 'Person 7')]
        persons2*.department = departments[1]
        persons2*.save(insert: true)
    }



    @Override
    Class<? extends Page> getHomePage() {
        HomePage
    }

    static ApplicationContext initGorm() {
        def init = new HibernateDatastoreSpringInitializer('test.domain')
        def dataSource = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", 'sa', '')
        dataSource.setDriverClassName(Driver.name)
        init.setConfiguration(new Properties(['hibernate.hbm2ddl.auto': 'create', 'hibernate.flushMode':'manual']))
        init.configureForDataSource(dataSource)
    }
}
