package edu.spring.btp.config

import edu.spring.btp.services.DbUserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
                .authorizeHttpRequests() { authorizeHttpRequests ->
                    authorizeHttpRequests.requestMatchers("/", "/login", "/signup", "/css/**", "domain").permitAll()
                    authorizeHttpRequests.requestMatchers("/error/**","/init/**").permitAll()
                    authorizeHttpRequests.anyRequest().authenticated()
                }
                .formLogin { formLogin ->
                    formLogin
                            .loginPage("/login")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/login/error")
                }
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
}