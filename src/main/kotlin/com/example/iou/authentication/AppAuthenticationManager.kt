package com.example.iou.authentication

import io.jsonwebtoken.Claims
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.stream.Collectors

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
                val rolesMap: List<String> = claims.get("role", List::class.java) as List<String>
                UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    rolesMap.stream()
                        .map(::SimpleGrantedAuthority)
                        .collect(Collectors.toList())
                )
            };
    }
}