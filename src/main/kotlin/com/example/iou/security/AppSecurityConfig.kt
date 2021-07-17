package com.example.iou.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
class AppSecurityConfig(
    private val authenticationManager: ReactiveAuthenticationManager,
) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/login", "/api/v*/register").permitAll()
            .anyExchange().authenticated()
            .and().build();
    }
}