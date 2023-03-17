package edu.spring.dogs.services

import edu.spring.dogs.entities.Role
import edu.spring.dogs.entities.User
import edu.spring.dogs.repositories.RoleRepository
import edu.spring.dogs.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

class DbUserService: UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val user= userRepository.findByUsernameOrEmail(username!!) ?: throw UsernameNotFoundException("User not found")
        return org.springframework.security.core.userdetails.User(user.username,user.password, getGrantedAuthorities(user))
    }

    private fun getGrantedAuthorities(user: User): List<GrantedAuthority>? {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        val role: Role = roleRepository.findByName(user.role) ?: throw UsernameNotFoundException("Role not found")
        authorities.add(SimpleGrantedAuthority(role.name))
        return authorities
    }

    fun encodePassword(user: User) {
        user.password=passwordEncoder.encode(user.password)
    }
}