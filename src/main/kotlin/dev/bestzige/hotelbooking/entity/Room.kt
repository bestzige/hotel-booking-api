package dev.bestzige.hotelbooking.entity

import dev.bestzige.hotelbooking.entity.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import lombok.Data

@Data
@Entity
@Table(name = "rooms")
data class Room(
    var name: String,

    var description: String? = null,

    @field:Min(1)
    var capacity: Int = 2,

    @field:Min(1)
    var price: Double,
) : BaseEntity()
