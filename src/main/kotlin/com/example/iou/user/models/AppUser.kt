package com.example.iou.user.models

//@Document
data class AppUser(
    var firstName: String,
    var lastName: String,
    var phone: String,
) {
    lateinit var id: String
}
