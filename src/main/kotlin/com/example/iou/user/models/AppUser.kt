package com.example.iou.user.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
class AppUser(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    password: String,
) :
    UserDetails {
    @Id
    lateinit var id: String
    var password: String = password
        @JvmName("password") get;

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf();
    }

    override fun getUsername(): String = this.email

    override fun getPassword(): String = this.password

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}
