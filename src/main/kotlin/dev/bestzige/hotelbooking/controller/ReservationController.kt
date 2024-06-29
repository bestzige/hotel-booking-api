package dev.bestzige.hotelbooking.controller

import dev.bestzige.hotelbooking.dto.request.ReservationRequest
import dev.bestzige.hotelbooking.dto.request.toReservation
import dev.bestzige.hotelbooking.service.ReservationService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/reservations")
@Validated
@PreAuthorize("hasAuthority('ADMIN')")
class ReservationController {
    @Autowired
    lateinit var reservationService: ReservationService

    @GetMapping
    fun findAll() = reservationService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = reservationService.findById(id)

    @PostMapping
    fun create(@Valid @RequestBody body: ReservationRequest) = reservationService.create(body.toReservation())

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody body: ReservationRequest) =
        reservationService.update(id, body.toReservation())

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) = reservationService.deleteById(id)
}