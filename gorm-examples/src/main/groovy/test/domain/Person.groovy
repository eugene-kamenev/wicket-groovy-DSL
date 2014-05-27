package test.domain

import grails.persistence.Entity
import groovy.transform.ToString

@Entity
@ToString(includes = ['name'])
class Person implements Serializable {
    String name
    static belongsTo = [department: Department]
}
