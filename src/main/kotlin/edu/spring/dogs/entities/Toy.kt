package edu.spring.dogs.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Toy() {

    @Id
    @GeneratedValue
    private var id: Int = 0
    private var type: String? = null
    private var label: String = ""

    constructor(type: String?, label: String) : this() {
        this.type = type
        this.label = label
    }


}