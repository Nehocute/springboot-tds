package edu.spring.dogs.entities

import jakarta.persistence.*

@Entity
open class Master() {

    @Id
    @GeneratedValue
    open var id: Int = 0
    open var firstname: String? = null
    open var lastname: String? = null


    constructor(firstname: String?, lastname: String?) : this() {
        this.firstname = firstname
        this.lastname = lastname
    }

    @OneToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    open var dogs = mutableSetOf<Dog>()

    fun addDog(dog: Dog) {
        dogs.add(dog)
        dog.master = this
    }

    fun giveUpDog(dog: Dog) {
        dogs.remove(dog)
        dog.master = null
    }

    @PreRemove
    fun preRemove(): Unit{
        if(dogs!=null){
            for(dog in dogs!!){
                dog.master=null
            }
        }
    }



}