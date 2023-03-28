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
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        http.authorizeHttpRequests {authorizeHttpRequests->
            authorizeHttpRequests.requestMatchers("/", "/css/**", "/domain/**", "/login/**", "/signup/**").permitAll()

            //Temporary
            authorizeHttpRequests.requestMatchers("/error/**", "/init/**").permitAll()
            authorizeHttpRequests.requestMatchers(PathRequest.toH2Console()).permitAll()


            authorizeHttpRequests.anyRequest().authenticated()
        }
        http.headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login/error?error=credentials")

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