package com.example.iou.authentication

import io.jsonwebtoken.Claims
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AppAuthenticationManager(
    private val jwtUtil: JWTUtil,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken: String = authentication.credentials.toString()
        val username: String = jwtUtil.getUsernameFromToken(authToken)
        return Mono.just(jwtUtil.validateToken(authToken))
            .filter { it != null }
            .switchIfEmpty(Mono.empty())
            .map {
                val claims: Claims = jwtUtil.getAllClaimsFromToken(authToken)
                val roleList: List<Map<String, String>> =
                    claims.get("role", List::class.java) as List<Map<String, String>>
                val roles = roleList.map { it["authority"] }.map(::SimpleGrantedAuthority)
                UsernamePasswordAuthenticationToken(
                    username,
                    authentication.credentials,
                    roles
                )
            };
    }
}