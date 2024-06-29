package dev.bestzige.hotelbooking.service

import dev.bestzige.hotelbooking.entity.Room
import dev.bestzige.hotelbooking.repository.RoomRepository
import dev.bestzige.hotelbooking.service.base.BaseService
import org.springframework.stereotype.Service

@Service
class RoomService(
    roomRepository: RoomRepository
) : BaseService<RoomRepository, Room>(roomRepository)