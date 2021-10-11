package com.example.iou.user

import com.example.iou.models.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/users")
class UserController(private val appUserService: AppUserService) {

    @GetMapping
    fun getUsers(): Flux<UserResponse> {
        return appUserService.getUsers()
    }

    @GetMapping("/me")
    fun getMe(): Mono<UserResponse> {
        return appUserService.getCurrentUser()
    }
}