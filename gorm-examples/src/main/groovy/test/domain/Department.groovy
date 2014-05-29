package test.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode(includes = ['id'])
class Department implements Serializable {
    String title
    Set<Person> persons = new HashSet<>()
    static hasMany = [persons: Person]
    static fetchMode = [persons: 'eager']

    static mapping = {
        persons lazy: false
    }
}
