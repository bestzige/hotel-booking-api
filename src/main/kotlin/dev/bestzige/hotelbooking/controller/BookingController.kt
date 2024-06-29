package dev.bestzige.hotelbooking.controller

import dev.bestzige.hotelbooking.dto.request.ReservationRequest
import dev.bestzige.hotelbooking.dto.request.toReservation
import dev.bestzige.hotelbooking.dto.response.BookingResponse
import dev.bestzige.hotelbooking.entity.Reservation
import dev.bestzige.hotelbooking.service.ReservationService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/bookings")
@Validated
class BookingController {
    @Autowired
    lateinit var reservationService: ReservationService

    @GetMapping
    fun findAllUserBookings(principal: Principal): ResponseEntity<Set<BookingResponse>> {
        val bookings = reservationService.findByUserId(principal.name.toLong())
        return ResponseEntity.ok(bookings.map { BookingResponse.from(it) }.toSet())
    }

    @PostMapping
    fun createBooking(principal: Principal, @Valid @RequestBody body: ReservationRequest): ResponseEntity<BookingResponse> {
        val reservation = body.toReservation()
        reservation.userId = principal.name.toLong()
        val booking = reservationService.create(reservation)
        return ResponseEntity.ok(BookingResponse.from(booking))
    }
}