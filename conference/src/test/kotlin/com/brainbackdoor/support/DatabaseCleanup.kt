package com.brainbackdoor.support

import com.google.common.base.CaseFormat
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.metamodel.EntityType
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

@Service
@ActiveProfiles("test")
class DatabaseCleanup : InitializingBean {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private lateinit var tableNames: List<String>
    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities.stream()
            .filter { e: EntityType<*> ->
                e.javaType.getAnnotation(Entity::class.java) != null
            }
            .map { e: EntityType<*> ->
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.name)
            }
            .collect(Collectors.toList())
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        tableNames = tableNames.filterNot { it == "role" }.toList()
        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
