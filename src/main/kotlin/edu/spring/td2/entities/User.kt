package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    @Column(length = 60, nullable = false, unique = true)
    open lateinit var name: String

    @Column(length = 40)
    open var email: String? = null

    @Column(length = 20)
    open var aliases: String? = null
}