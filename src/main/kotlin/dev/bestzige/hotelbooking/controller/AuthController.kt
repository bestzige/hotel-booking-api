package dev.bestzige.hotelbooking.controller

import dev.bestzige.hotelbooking.dto.request.SignInRequest
import dev.bestzige.hotelbooking.dto.request.SignUpRequest
import dev.bestzige.hotelbooking.dto.response.AuthResponse
import dev.bestzige.hotelbooking.entity.User
import dev.bestzige.hotelbooking.enum.Role
import dev.bestzige.hotelbooking.service.TokenService
import dev.bestzige.hotelbooking.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/auth")
@Validated
class AuthController {

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var tokenService: TokenService
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @GetMapping("/profile")
    fun profile(principal: Principal): ResponseEntity<User> {
        val user = userService.findById(principal.name.toLong()) ?: throw IllegalArgumentException("User not found")
        return ResponseEntity.ok(user)
    }

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody request: SignUpRequest): ResponseEntity<AuthResponse> {
        val user = userService.create(
            User(
                name = request.name,
                email = request.email,
                hashedPassword = request.password,
                role = Role.USER
            )
        )

        val token = tokenService.generate(user)

        return ResponseEntity.ok(AuthResponse(token!!))
    }

    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody request: SignInRequest): ResponseEntity<AuthResponse> {
        val user = userService.findByEmail(request.email) ?: throw IllegalArgumentException("Bad credentials")
        val usernamePassword = UsernamePasswordAuthenticationToken(user.id, request.password)
        val auth = authenticationManager.authenticate(usernamePassword)

        val token = tokenService.generate(auth.principal as User)

        return ResponseEntity.ok(AuthResponse(token!!))
    }
}