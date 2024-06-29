package dev.bestzige.hotelbooking.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @field:NotBlank
    @field:Size(min = 3)
    var name: String,

    @field:NotBlank
    @field:Email
    var email: String,

    @field:NotBlank
    @field:Size(min = 6)
    var password: String
)