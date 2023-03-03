package edu.spring.dogs.repositories

import edu.spring.dogs.entities.Dog
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DogRepository: CrudRepository<Dog, Int> {

    fun findByNameAndMasterId(name: String, masterId: Int): Dog
    fun findByMasterIsNull(): Set<Dog>

}
