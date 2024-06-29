package dev.bestzige.hotelbooking.service.base

import dev.bestzige.hotelbooking.entity.base.BaseEntity
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

abstract class BaseService<out R : JpaRepository<T, Long>, T : BaseEntity>(
    private val repository: R
) : BaseServiceInterface<T> {

    @Transactional(readOnly = true)
    override fun findAll(): List<T> {
        return repository.findAll()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): T? {
        return repository.findById(id).orElseThrow { EntityNotFoundException("Entity not found with id: $id") }
    }

    @Transactional
    override fun create(entity: T): T {
        return repository.save(entity)
    }

    @Transactional
    override fun update(id: Long, entity: T): T? {
        val existingEntity =
            repository.findById(id).orElseThrow { EntityNotFoundException("Entity not found with id: $id") }
        entity.id = id
        entity.version = existingEntity.version
        return repository.save(entity)
    }

    @Transactional
    override fun deleteById(id: Long): T? {
        val entity = repository.findById(id).orElseThrow { EntityNotFoundException("Entity not found with id: $id") }
        repository.delete(entity)
        return entity
    }
}