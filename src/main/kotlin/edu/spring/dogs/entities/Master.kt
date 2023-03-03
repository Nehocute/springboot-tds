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

    @OneToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST], fetch = FetchType.LAZY)
    open var dogs = mutableSetOf<Dog>()

    fun addDog(dog:Dog): Boolean{
        if(dogs==null){
            dogs=HashSet<Dog>()
        }
        return dogs!!.add(dog)
    }

    fun giveUpDog(dog:Dog): Boolean{
        if(dogs==null){
            dogs=HashSet<Dog>()
        }
        return dogs!!.remove(dog)
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