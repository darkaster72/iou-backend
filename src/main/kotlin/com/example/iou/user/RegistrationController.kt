package com.example.iou.user

import com.example.iou.models.UserDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/v1/register")
class RegistrationController(private val registrationService: RegistrationService) {

    @PostMapping
    fun register(@RequestBody request: Mono<UserDto>): ResponseEntity<Any> {
        return ResponseEntity.of(Optional.of(registrationService.register(request)))
    }
}