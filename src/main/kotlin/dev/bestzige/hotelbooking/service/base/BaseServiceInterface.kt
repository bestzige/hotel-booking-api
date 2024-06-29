package dev.bestzige.hotelbooking.service.base

interface BaseServiceInterface<T> {
    fun findAll(): List<T>
    fun findById(id: Long): T?
    fun create(entity: T): T
    fun update(id: Long, entity: T): T?
    fun deleteById(id: Long): T?
}