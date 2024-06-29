package dev.bestzige.hotelbooking.controller

import dev.bestzige.hotelbooking.dto.request.UserRequest
import dev.bestzige.hotelbooking.dto.request.toUser
import dev.bestzige.hotelbooking.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('ADMIN')")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @GetMapping
    fun findAll() = userService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = userService.findById(id)

    @PostMapping
    fun create(@Valid @RequestBody body: UserRequest) = userService.create(body.toUser())

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody body: UserRequest) = userService.update(id, body.toUser())

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) = userService.deleteById(id)
}