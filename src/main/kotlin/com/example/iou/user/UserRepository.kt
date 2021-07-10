package com.example.iou.user

import com.example.iou.user.models.AppUser
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : ReactiveMongoRepository<AppUser, String> {
}