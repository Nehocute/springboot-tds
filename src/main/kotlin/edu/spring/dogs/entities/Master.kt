package edu.spring.dogs.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.OneToMany
import jakarta.persistence.Id
import jakarta.persistence.PreRemove

@Entity
class Master() {

    @Id
    @GeneratedValue
    var id: Int = 0
    var firstname: String? = null
    var lastname: String? = null


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