package dev.bestzige.hotelbooking.filter

import dev.bestzige.hotelbooking.repository.UserRepository
import dev.bestzige.hotelbooking.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader("Authorization") ?: null
        val token = authHeader?.replace("Bearer ", "")
        token?.let {
            val subject = tokenService.validate(it)
            val user = subject?.toLong()?.let { it1 -> this.userRepository.findById(it1) }
            val authentication = UsernamePasswordAuthenticationToken(user?.get()?.id, null, user?.get()?.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

}