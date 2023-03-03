package edu.spring.dogs.entities

class Toy() {

    private var id: Int = 0
    private var type: String? = null
    private var label: String = ""

    constructor(type: String?, label: String) : this() {
        this.type = type
        this.label = label
    }


}