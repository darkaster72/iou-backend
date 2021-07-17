package com.example.iou.user

import com.example.iou.models.UserDto
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono

@Service
class RegistrationService(
    private val userService: AppUserService,
) {
    fun register(@RequestBody userDto: Mono<UserDto>): Mono<RegistrationResponse> {
        return userService.addUser(userDto).map { RegistrationResponse() }
    }
}