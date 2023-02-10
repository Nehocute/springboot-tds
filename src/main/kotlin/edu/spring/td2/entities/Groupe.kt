package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
open class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    @Column(length = 20)
    open lateinit var firstname: String

    @Column(length = 20)
    open lateinit var lastname: String

    @Column(length = 40)
    open var email: String? = null

    @Column(length = 40)
    open var password: String? = null
}