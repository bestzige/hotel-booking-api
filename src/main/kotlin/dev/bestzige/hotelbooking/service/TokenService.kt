package dev.bestzige.hotelbooking.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import dev.bestzige.hotelbooking.entity.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {
    @Value("{jwt.secret}")
    lateinit var secret: String

    fun generate(user: User): String? {
        return try {
            JWT.create()
                .withIssuer("bestzige-api")
                .withSubject(user.id.toString())
                .withExpiresAt(
                    LocalDateTime.now().plusSeconds(3600)
                        .toInstant(ZoneOffset.ofHours(7))
                )
                .sign(Algorithm.HMAC256(secret))
        } catch (exception: JWTCreationException) {
            throw RuntimeException("Error creating token", exception)
        }
    }

    fun validate(token: String): String? {
        return try {
            JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("bestzige-api")
                .build()
                .verify(token)
                .subject
        } catch (exception: JWTVerificationException) {
            return ""
        }
    }
}