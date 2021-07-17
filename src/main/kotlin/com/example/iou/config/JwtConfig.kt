package com.example.iou.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
class JwtConfig() {
    lateinit var secret: String
    lateinit var tokenPrefix: String
    lateinit var tokenExpirationAfterHours: Integer

    val authorizationHeader: String
        get() = HttpHeaders.AUTHORIZATION
}