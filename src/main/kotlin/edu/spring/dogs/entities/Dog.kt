package edu.spring.dogs.entities

import jakarta.persistence.*

@Entity
open class Dog() {
    @Id
    @GeneratedValue
    open var id: Int = 0
    open var name: String = ""

    constructor(name: String) : this() {
        this.name = name
    }

    @ManyToOne()
    @JoinColumn(name = "master_id")
    open var master: Master? = null

    @ManyToMany()
    open var toys: MutableSet<Toy> = HashSet<Toy>()

}