package com.example.iou.user

import com.example.iou.models.UserDto
import com.example.iou.user.models.AppUser
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AppUserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
        return userRepository.findByUsername(username);
    }

    fun getUsers(): Flux<UserDto> {
        return userRepository.findAll().map { it.toDto() }
    }

    fun addUser(user: Mono<UserDto>): Mono<UserDto> {
        return user.map { it.toAppUser() }
            .map { it.copyWith(password = passwordEncoder.encode(it.password)) }
            .flatMap { userRepository.insert(it) }
            .map { it.toDto() }
    }

    fun UserDto.toAppUser(): AppUser = AppUser(
        firstName = firstName,
        lastName = lastName,
        email = email,
        username = email,
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

    fun AppUser.copyWith(password: String) = AppUser(
        firstName = firstName,
        lastName = lastName,
        email = email,
        username = username,
        password = password,
        phone = phone
    )
}