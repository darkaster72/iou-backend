package com.example.iou.user

import com.example.iou.models.UserDto
import com.example.iou.user.models.AppUser
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AppService(private val userRepository: UserRepository) {

    fun getUsers(): Flux<UserDto> {
        return userRepository.findAll().map { it.toDto() }
    }

    fun addUser(user: Mono<UserDto>): Mono<UserDto> {
        return user.map { it.toAppUser() }
            .flatMap { userRepository.insert(it) }
            .map { it.toDto() }
    }

    fun UserDto.toAppUser(): AppUser = AppUser(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        phone = phone
    )

    fun AppUser.toDto(): UserDto = UserDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        phone = phone
    )
}