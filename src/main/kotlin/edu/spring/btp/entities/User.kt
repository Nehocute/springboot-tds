package edu.spring.btp.entities

import jakarta.persistence.*

@Entity
open class User() {

    constructor(username:String):this(){
        this.username=username
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id:Int=0

    @Column(length = 65, nullable = false, unique = true)
    open lateinit var username:String

    @Column(length = 256)
    open var password:String?=null

    @Column(unique = true, length = 115)
    open var email:String?=null

    @Column(nullable = false)
    open lateinit var role:String

    @OneToMany(mappedBy = "user")
    open var complaints= mutableListOf<Complaint>()

    @ManyToMany(mappedBy = "claimants", cascade = [CascadeType.REMOVE])
    open var claims= mutableListOf<Complaint>()

    @PreRemove
    fun preRemove(){
        for (complaint in complaints) {
            complaint.user=null
        }
    }

}