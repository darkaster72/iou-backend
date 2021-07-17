package com.example.iou.authentication

import com.example.iou.user.AppUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/login")
class LoginController(
    private val appUserService: AppUserService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JWTUtil,
) {

    @PostMapping
    fun login(@RequestBody request: AuthenticationRequest): Mono<ResponseEntity<AuthenticationResponse>> {
        return appUserService.findByUsername(request.username)
            .filter { passwordEncoder.matches(request.password, it.password) }
            .map { ResponseEntity.ok(AuthenticationResponse(generateToken(it))) }
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    private fun generateToken(userDetails: UserDetails): String {
        return jwtUtil.generateToken(userDetails)
    }

}