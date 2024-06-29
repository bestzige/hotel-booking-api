package dev.bestzige.hotelbooking.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.bestzige.hotelbooking.entity.base.BaseEntity
import dev.bestzige.hotelbooking.enum.Role
import jakarta.persistence.*
import lombok.Data
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Data
@Entity
@Table(name = "users")
data class User(
    @Column(nullable = false)
    var name: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @JsonIgnore
    @Column(nullable = false, name = "password")
    var hashedPassword: String,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER
) : BaseEntity(), UserDetails {
    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return true
    }

    @JsonIgnore
    override fun getUsername(): String {
        return id.toString()
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun getPassword(): String {
        return hashedPassword
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
