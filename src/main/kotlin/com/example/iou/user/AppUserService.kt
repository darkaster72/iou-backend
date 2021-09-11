package com.example.iou.user

import com.example.iou.models.UserRequest
import com.example.iou.models.UserResponse
import com.example.iou.user.models.AppUser
import com.example.iou.user.models.AppUserView
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

    fun findByUsernameViewOnly(username: String?): Mono<AppUserView> {
        return userRepository.findViewByUsername(username);
    }

    fun getIdByUsername(username: String?): Mono<String> {
        return userRepository.findViewByUsername(username).map { it.id };
    }

    fun userExists(user: UserRequest): Mono<Boolean> {
        return userRepository.existsByUsername(user.email)
    }

    fun getUsers(): Flux<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

    fun addUser(user: UserRequest): Mono<UserResponse> {
        val appUser = user.toAppUser().copyWith(password = passwordEncoder.encode(user.password))
        return userRepository.insert(appUser)
            .map { it.toResponse() }
    }

    fun UserRequest.toAppUser(): AppUser = AppUser(
        firstName = firstName,
        lastName = lastName,
        email = email,
        username = email,
        password = password,
        phone = phone,
        userRoles = roles
    )

    fun AppUser.toResponse(): UserResponse = UserResponse(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone
    )

    fun AppUser.copyWith(password: String) = AppUser(
        firstName = firstName,
        lastName = lastName,
        email = email,
        username = username,
        password = password,
        phone = phone,
        userRoles = userRoles
    )
}