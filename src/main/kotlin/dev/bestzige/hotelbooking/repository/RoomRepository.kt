package dev.bestzige.hotelbooking.repository

import dev.bestzige.hotelbooking.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository : JpaRepository<Room, Long> {
    fun findByCapacityGreaterThanEqual(capacity: Int): List<Room>
}