package edu.spring.dogs.entities

import jakarta.persistence.*

@Entity
open class Toy() {

    @Id
    @GeneratedValue
    private var id: Int = 0
    open var type: String? = null
    open var label: String = ""

    constructor(type: String?, label: String) : this() {
        this.type = type
        this.label = label
    }

    @ManyToMany()
    @JoinTable(name = "dog_toy", joinColumns = [JoinColumn(name = "toy_id")], inverseJoinColumns = [JoinColumn(name = "dog_id")])
    open var dogs = mutableSetOf<Dog>()


}