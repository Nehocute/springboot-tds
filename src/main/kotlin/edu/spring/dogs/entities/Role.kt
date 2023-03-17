package edu.spring.dogs.entities

import jakarta.persistence.*

@Entity
open class Role {

    constructor(name:String) :this(){
        this.name=name
    }

    constructor()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id:Int=0

    @Column(length = 65, nullable = false)
    open lateinit var name:String

}