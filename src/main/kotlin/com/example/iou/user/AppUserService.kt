package com.example.iou.user

import com.example.iou.models.UserDto
import com.example.iou.user.models.AppUser
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AppUserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
        return userRepository.findByUsername(username);
    }

    fun userExists(user: UserDto): Mono<Boolean> {
        return userRepository.existsByUsername(user.email)
    }

    fun getUsers(): Flux<UserDto> {
        return userRepository.findAll().map { it.toDto() }
    }

    fun addUser(user: UserDto): Mono<UserDto> {
        val appUser = user.toAppUser().copyWith(password = passwordEncoder.encode(user.password))
        return userRepository.insert(appUser)
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