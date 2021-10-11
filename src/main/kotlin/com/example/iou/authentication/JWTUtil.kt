package com.example.iou.authentication

import com.example.iou.config.JwtConfig
import io.jsonwebtoken.ClaimJwtException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct

@Component
class JWTUtil(private val jwtConfig: JwtConfig) {
    private lateinit var key: Key;

    @PostConstruct
    fun init() {
        this.key = Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray())
    }

    @Throws(ClaimJwtException::class)
    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getAllClaimsFromToken(token).expiration
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(user: UserDetails): String {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["role"] = user.authorities.map { SimpleGrantedAuthority("ROLE_${it}") }
        return doGenerateToken(claims, user.username)
    }

    private fun doGenerateToken(claims: Map<String, Any?>, username: String): String {
        val expirationTimeHours: Long = jwtConfig.tokenExpirationAfterHours.toLong() //in second
        val createdDate = Date()
        val expirationDate = Date(createdDate.time + expirationTimeHours * 60 * 60 * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            !isTokenExpired(token)
        } catch (e: ClaimJwtException) {
            false;
        }
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }

}
