package com.example.iou.user

import com.example.iou.models.UserRequest
import com.example.iou.models.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("api/v1/users")
class UserController(private val appUserService: AppUserService) {

    @GetMapping
    fun getUsers(): Flux<UserResponse> {
        return appUserService.getUsers()
    }

    @PostMapping
    fun addUser(@RequestBody userRequest: Mono<UserRequest>): Mono<UserResponse> {
        return userRequest.flatMap { appUserService.addUser(it) }
    }

    @GetMapping("/me")
    fun getMe(principal: Principal): Mono<ResponseEntity<Principal>> {
        return Mono.justOrEmpty(principal)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
    }
}