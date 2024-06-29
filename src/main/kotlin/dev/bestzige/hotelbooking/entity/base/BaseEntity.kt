package dev.bestzige.hotelbooking.entity.base

import jakarta.persistence.*
import lombok.Data

@Data
@MappedSuperclass
open class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Version
    var version: Long = 0
)
