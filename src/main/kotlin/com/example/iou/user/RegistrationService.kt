package com.example.iou.user

import com.example.iou.models.UserRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RegistrationService(
    private val userService: AppUserService,
) {
    fun register(userRequest: UserRequest): Mono<RegistrationResponse> {
        return userService
            .userExists(userRequest)
            .flatMap { userExists ->
                if (userExists)
                    Mono.just(RegistrationResponse(error = "User already exists", success = false))
                else
                    this.userService.addUser(userRequest).map { RegistrationResponse() }
            }
    }
}