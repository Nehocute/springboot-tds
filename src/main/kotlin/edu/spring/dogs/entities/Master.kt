package edu.spring.dogs.entities

import jakarta.persistence.OneToMany
import jakarta.persistence.PreRemove

class Master() {

    var firstname: String? = null
    var lastname: String? = null
    var id: Int = 0

    constructor(firstname: String?, lastname: String?) : this() {
        this.firstname = firstname
        this.lastname = lastname
    }

    @OneToMany(cascade = [jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.PERSIST], mappedBy = "master")
    var dogs: MutableSet<Dog>? = null

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