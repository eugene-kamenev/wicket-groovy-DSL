package test.domain
import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode(includes = ['id'])
class Person implements Serializable {
    String name
    static belongsTo = [department: Department]

    static fetchMode = [department: 'eager']

    static mapping = {
        department lazy: false
    }
    static constraints = {
        department nullable: true
    }
}
