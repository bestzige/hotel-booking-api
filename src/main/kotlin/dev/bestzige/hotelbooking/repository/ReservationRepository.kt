package dev.bestzige.hotelbooking.repository

import dev.bestzige.hotelbooking.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {
    fun findByUserId(userId: Long): List<Reservation>
    fun findByRoomId(roomId: Long): List<Reservation>
    fun findByUserIdAndRoomId(userId: Long, roomId: Long): List<Reservation>
    fun existsByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        roomId: Long,
        startDate: Date,
        endDate: Date
    ): Boolean

    fun existsByUserIdAndRoomId(userId: Long, roomId: Long): Boolean
}