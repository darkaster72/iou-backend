package com.example.iou.user

data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
) {

}
