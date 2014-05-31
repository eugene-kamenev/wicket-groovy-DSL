package test.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import wicket.groovy.gorm.core.domain.GormEntity

@Entity
@EqualsAndHashCode(includes = ['id'])
class Department extends GormEntity {
    String title
    Set<Person> persons = new HashSet<>()
    Boolean enabled


    static hasMany = [persons: Person]

    static mapping = {
        persons cascade: 'all'
    }

    static constraints = {
        enabled nullable: true
        persons form: [choices: { Person.list() }, render: [id: 'id', value: 'name']]
    }
}
