package com.example.iou.models

import com.example.iou.user.models.AppUserRole

data class UserRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val password: String,
    val roles: List<AppUserRole> = listOf(AppUserRole.USER),
)

data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val roles: List<AppUserRole> = listOf(AppUserRole.USER),
);
