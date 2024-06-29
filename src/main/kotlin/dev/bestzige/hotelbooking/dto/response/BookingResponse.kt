package dev.bestzige.hotelbooking.dto.response

import dev.bestzige.hotelbooking.entity.Reservation
import dev.bestzige.hotelbooking.entity.Room
import java.util.*

data class BookingResponse(
    val id: Long,
    val startDate: Date,
    val endDate: Date,
    val past: Boolean = false,
    val room: Room? = null
) {
    companion object {
        fun from(reservation: Reservation): BookingResponse {
            return BookingResponse(
                id = reservation.id,
                startDate = reservation.startDate,
                endDate = reservation.endDate,
                past = reservation.endDate.before(Date()),
                room = reservation.room
            )
        }
    }
}