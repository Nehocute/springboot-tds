package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    @Column(length = 255, nullable = false)
    open lateinit var firstname: String

    @Column(length = 255, nullable = false)
    open lateinit var lastname: String

    @Column(length = 255, nullable = false, unique = true)
    open var email: String? = null

//    @Column(length = 40, nullable = false)
//    open var password: String? = null

    open var suspended: Boolean = false

    @ManyToOne
    @JoinColumn(name = "idOrganization", nullable = false)
    open lateinit var organization:Organization

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_groups")
    open val groups = mutableSetOf<Group>()

}