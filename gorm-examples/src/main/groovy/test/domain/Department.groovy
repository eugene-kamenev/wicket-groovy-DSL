package test.domain

import grails.persistence.Entity

@Entity
class Department implements Serializable {
    String title
    static hasMany = [persons: Person]
}
