package edu.spring.dogs.entities

import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

class Dog() {

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