package com.example.iou.user

import com.example.iou.models.UserDto
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/users")
class UserController(private val appUserService: AppUserService) {

    @GetMapping
    fun getUsers(): Flux<UserDto> {
        return appUserService.getUsers()
    }

    @PostMapping
    fun addUser(@RequestBody userDto: Mono<UserDto>): Mono<UserDto> {
        return appUserService.addUser(userDto)
    }
}