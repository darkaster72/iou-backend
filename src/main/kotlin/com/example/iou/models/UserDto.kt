package com.example.iou.models

import com.example.iou.user.models.AppUserRole

data class UserDto(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val password: String,
    val roles: List<AppUserRole> = listOf(AppUserRole.USER),
) {
}
