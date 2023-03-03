package edu.spring.dogs.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToOne

@Entity
class Dog() {
    @Id
    @GeneratedValue
    var id: Int = 0
    var name: String = ""

    constructor(name: String) : this() {
        this.name = name
    }

    @OneToOne(cascade = [jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.PERSIST], mappedBy = "dog")
    var master: Master? = null

    @ManyToMany(cascade = [jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.PERSIST], mappedBy = "dog")
    var toys: MutableSet<Toy>? = null

}