package edu.spring.dogs.repositories

import edu.spring.dogs.entities.Role
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface RoleRepository: CrudRepository<Role, Int> {

    @Query("SELECT r FROM Role r WHERE r.name=:name")
    fun findByName(name:String):Role?
}