package dev.bestzige.hotelbooking.dto.request

import dev.bestzige.hotelbooking.entity.Reservation
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class ReservationRequest(
    @field:NotNull
    var roomId: Long,

    @field:NotNull
    var userId: Long,

    @field:NotBlank
    @field:Pattern(
        regexp = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$",
        message = "Invalid date format. Expected format: dd-MM-yyyy HH:mm:ss"
    )
    var startDate: String,

    @field:NotBlank
    @field:Pattern(
        regexp = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$",
        message = "Invalid date format. Expected format: dd-MM-yyyy HH:mm:ss"
    )
    var endDate: String
)

fun ReservationRequest.toReservation(): Reservation {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    val startLocalDateTime = LocalDateTime.parse(startDate, formatter)
    val endLocalDateTime = LocalDateTime.parse(endDate, formatter)

    return Reservation(
        roomId = roomId,
        userId = userId,
        startDate = Date.from(startLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()),
        endDate = Date.from(endLocalDateTime.atZone(ZoneId.systemDefault()).toInstant())
    )
}