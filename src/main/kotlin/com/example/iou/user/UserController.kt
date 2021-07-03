package com.example.iou.user

import com.example.iou.user.models.AppUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/users")
class UserController {

    @GetMapping
    fun getUsers(): Mono<AppUser> {
        val appUser = AppUser(firstName = "Ankit", lastName = "Sharma", phone = "8274800555")
        appUser.id = "Test"
        return Mono.just(appUser)
    }
}