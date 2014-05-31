package test.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import wicket.groovy.gorm.core.domain.GormEntity

@Entity
@EqualsAndHashCode(includes = ['id', 'name'])
class Person extends GormEntity {
    String name
    String password
    String email

    static belongsTo = [department: Department]

    static constraints = {
        department nullable: true, form: [choices: { Department.findAll() },
                                          render : ['id': 'id', 'value': 'title']]
        password password: true, nullable: false
        email email: true, unique: true
    }
}
