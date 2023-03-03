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

    @ManyToMany(mappedBy = "toys", cascade = [CascadeType.MERGE, CascadeType.PERSIST], fetch = FetchType.LAZY)
    open var dogs = mutableSetOf<Dog>()


}