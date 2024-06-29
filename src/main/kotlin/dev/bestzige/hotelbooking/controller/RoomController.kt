package dev.bestzige.hotelbooking.controller

import dev.bestzige.hotelbooking.dto.request.RoomRequest
import dev.bestzige.hotelbooking.dto.request.toRoom
import dev.bestzige.hotelbooking.service.RoomService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/rooms")
@Validated
@PreAuthorize("hasAuthority('ADMIN')")
class RoomController {
    @Autowired
    lateinit var roomService: RoomService

    @GetMapping
    fun findAll() = roomService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = roomService.findById(id)

    @PostMapping
    fun create(@Valid @RequestBody body: RoomRequest) = roomService.create(body.toRoom())

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody body: RoomRequest) = roomService.update(id, body.toRoom())

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) = roomService.deleteById(id)
}