package com.example.iou.user

import com.example.iou.models.UserDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RegistrationService(
    private val userService: AppUserService,
) {
    fun register(userDto: UserDto): Mono<RegistrationResponse> {
        return userService
            .userExists(userDto)
            .flatMap { userExists ->
                if (userExists)
                    Mono.just(RegistrationResponse(error = "User already exists", success = false))
                else
                    this.userService.addUser(userDto).map { RegistrationResponse() }
            }
    }
}