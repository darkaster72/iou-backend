package com.example.iou.authentication

import com.example.iou.user.AppUserService
import io.jsonwebtoken.ClaimJwtException
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AppAuthenticationManager(
    private val jwtUtil: JWTUtil,
    private val appUserService: AppUserService,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken: String = authentication.credentials.toString()
        try {
            val username: String = jwtUtil.getUsernameFromToken(authToken)

            return Mono.just(jwtUtil.validateToken(authToken))
                .map {
                    val claims: Claims = jwtUtil.getAllClaimsFromToken(authToken)
                    val roleList: List<Map<String, String>> =
                        claims.get("role", List::class.java) as List<Map<String, String>>
                    val roles = roleList.map { it["authority"] }.map(::SimpleGrantedAuthority)
                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        username,
                        authentication.credentials,
                        roles
                    )
                    usernamePasswordAuthenticationToken
                }
                .flatMap { auth ->
                    appUserService.getIdByUsername(username)
                        .map {
                            auth.details = it
                            auth
                        }
                }
        } catch (e: ClaimJwtException) {
            return Mono.error(JwtExpired("Authentication failed"))
        }
    }
}

class JwtExpired(msg: String): AuthenticationException(msg);