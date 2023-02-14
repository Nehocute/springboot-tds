package edu.spring.td2.services

import edu.spring.td2.entities.Organization
import edu.spring.td2.entities.User
import org.springframework.stereotype.Service

@Service
class OrgaService {
    fun addUsersToOrga(orga: Organization, users:String) {
        users.split("\n").forEach {
            if (it.trim().isBlank()) return@forEach
            val user = User()
            val value = it.trim().split(" ", limit = 2)
            user.firstname = value[0]
            user.lastname = value[1]
            user.email = "${user.firstname}.${user.lastname}@${orga.domain}".lowercase()
            user.suspended = false
            orga.addUser(user)
        }

    }

}