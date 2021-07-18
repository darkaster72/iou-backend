package com.example.iou.user.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
class AppUser(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    username: String,
    password: String,
    val userRoles: List<AppUserRole>,
) :
    UserDetails {
    @Id
    lateinit var id: String
    var password: String = password
        @JvmName("password") get;

    var username: String = username
        @JvmName("username") get;

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return userRoles.map { SimpleGrantedAuthority(it.value) }
    }

    override fun getUsername(): String = this.email

    override fun getPassword(): String = this.password

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}

enum class AppUserRole(val value: String) {
    USER("USER"),
    ADMIN("ADMIN")
}
