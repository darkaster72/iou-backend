package com.example.iou.user.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

//@Document
class AppUser(
    var firstName: String,
    var lastName: String,
    var phone: String,
    var email: String,
    password: String,
) : UserDetails {
    lateinit var id: String
    private var _password = password;
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf();
    }

    override fun getPassword(): String {
        return this._password;
    }

    override fun getUsername(): String {
        return this.email;
    }

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}
