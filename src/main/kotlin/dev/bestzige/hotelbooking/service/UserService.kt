package dev.bestzige.hotelbooking.service

import dev.bestzige.hotelbooking.entity.User
import dev.bestzige.hotelbooking.repository.UserRepository
import dev.bestzige.hotelbooking.service.base.BaseService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
) : BaseService<UserRepository, User>(userRepository), UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(id: String): User? {
        return userRepository.findById(id.toLong()).orElse(null)
    }

    @Transactional(readOnly = true)
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    @Transactional
    override fun create(entity: User): User {
        userRepository.findByEmail(entity.email)?.let {
            throw IllegalArgumentException("User with email ${entity.email} already exists")
        }

        entity.hashedPassword = encoder.encode(entity.hashedPassword)
        return super.create(entity)
    }
}