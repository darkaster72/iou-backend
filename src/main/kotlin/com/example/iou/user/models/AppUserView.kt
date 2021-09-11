package com.example.iou.user.models

data class AppUserView(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val userRoles: List<AppUserRole>,
);