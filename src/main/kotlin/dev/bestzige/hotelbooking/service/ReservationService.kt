package dev.bestzige.hotelbooking.service

import dev.bestzige.hotelbooking.entity.Reservation
import dev.bestzige.hotelbooking.repository.ReservationRepository
import dev.bestzige.hotelbooking.repository.RoomRepository
import dev.bestzige.hotelbooking.repository.UserRepository
import dev.bestzige.hotelbooking.service.base.BaseService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository
) : BaseService<ReservationRepository, Reservation>(reservationRepository) {

    @Transactional(readOnly = true)
    fun findByUserId(userId: Long): List<Reservation> {
        return reservationRepository.findByUserId(userId)
    }

    @Transactional(readOnly = true)
    fun findByRoomId(roomId: Long): List<Reservation> {
        return reservationRepository.findByRoomId(roomId)
    }

    @Transactional
    override fun create(entity: Reservation): Reservation {
        validateRoomAndUser(entity)
        return super.create(entity)
    }

    @Transactional
    override fun update(id: Long, entity: Reservation): Reservation? {
        validateRoomAndUser(entity, true)
        return super.update(id, entity)
    }

    private fun validateRoomAndUser(entity: Reservation, isUpdate: Boolean = false) {
        // Check if the room exists
        roomRepository.findById(entity.roomId)
            .orElseThrow { IllegalArgumentException("Room with id ${entity.roomId} not found") }

        // Check if the user exists
        userRepository.findById(entity.userId)
            .orElseThrow { IllegalArgumentException("User with id ${entity.userId} not found") }

        // Skip the following checks if the entity is being updated
        if (isUpdate) return

        // Check if the user has already reserved the room
        if (reservationRepository.existsByUserIdAndRoomId(entity.userId, entity.roomId)) {
            throw IllegalArgumentException("User with id ${entity.userId} has already reserved the room with id ${entity.roomId}")
        }

        // Check if the room is already reserved for the given period
        if (reservationRepository.existsByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                entity.roomId, entity.endDate, entity.startDate
            )
        ) {
            throw IllegalArgumentException("Room with id ${entity.roomId} is already reserved for the given period")
        }

        return
    }
}