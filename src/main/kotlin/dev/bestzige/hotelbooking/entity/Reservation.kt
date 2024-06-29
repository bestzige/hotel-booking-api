package dev.bestzige.hotelbooking.entity

import dev.bestzige.hotelbooking.entity.base.BaseEntity
import jakarta.persistence.*
import lombok.Data
import java.util.*

@Data
@Entity
@Table(name = "reservations")
data class Reservation(
    var roomId: Long,

    var userId: Long,

    var startDate: Date,

    var endDate: Date,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roomId", insertable = false, updatable = false)
    val room: Room? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    val user: User? = null
) : BaseEntity()