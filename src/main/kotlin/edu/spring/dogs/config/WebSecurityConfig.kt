package edu.spring.dogs.config

import edu.spring.dogs.services.DbUserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class WebSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
                .authorizeHttpRequests{
                    it.requestMatchers("/css/**", "/assets/**", "/login/**").permitAll() // (1)
                    .requestMatchers("/admin/**").hasRole("ROLE_ADMIN") // (2)
                    .requestMatchers("/user/**").hasAuthority("USER") // (3)
                    .requestMatchers("/staff/**").hasAnyAuthority("USER","ADMIN","MANAGER") // (4)
                    .anyRequest().authenticated()
                    .requestMatchers("/init/**","/login/**").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .requestMatchers("/master/**").hasRole("manager")
                    .anyRequest().authenticated()
                            .and()
                            .headers().frameOptions().sameOrigin()
                            .and()
                            .formLogin().loginPage("/login").successForwardUrl("/")
                }
                .csrf().disable()
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return DbUserService()
    }
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchyImpl? {
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF > ROLE_USER > ROLE_GUEST")
        return roleHierarchy
    }

    @Bean
    fun expressionHandler(): DefaultWebSecurityExpressionHandler? {
        val expressionHandler = DefaultWebSecurityExpressionHandler()
        expressionHandler.setRoleHierarchy(roleHierarchy())
        return expressionHandler
    }
}