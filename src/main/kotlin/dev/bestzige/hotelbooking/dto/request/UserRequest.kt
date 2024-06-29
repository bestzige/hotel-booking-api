package dev.bestzige.hotelbooking.dto.request

import dev.bestzige.hotelbooking.entity.User
import dev.bestzige.hotelbooking.enum.Role
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequest(
    @field:NotBlank
    @field:Size(min = 3)
    var name: String,

    @field:NotBlank
    var email: String,

    @field:NotBlank
    @field:Size(min = 6)
    var password: String,

    @field:NotBlank
    var role: String?
)

fun UserRequest.toUser() = User(
    name = name,
    email = email,
    hashedPassword = password,
    role = Role.valueOf(role ?: "USER")
)