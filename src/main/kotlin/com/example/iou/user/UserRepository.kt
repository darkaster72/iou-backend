package com.example.iou.user

import com.example.iou.user.models.AppUser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveMongoRepository<AppUser, String> {
    fun findByUsername(username: String?): Mono<UserDetails>

    fun existsByUsername(username: String?): Mono<Boolean>

}