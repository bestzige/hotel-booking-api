package dev.bestzige.hotelbooking.dto.request

import dev.bestzige.hotelbooking.entity.Room
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RoomRequest(
    @field:NotBlank
    @field:Size(min = 3)
    var name: String,

    var description: String? = null,

    @field:Min(1)
    var capacity: Int = 2,

    @field:Min(1)
    var price: Double = 100.0,
)

fun RoomRequest.toRoom() = Room(
    name = name,
    description = description,
    capacity = capacity,
    price = price
)