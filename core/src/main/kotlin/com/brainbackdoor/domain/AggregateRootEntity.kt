package com.brainbackdoor.domain

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.brainbackdoor.support.RandomValueIdGenerator
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseAggregateRootEntity<A> : AbstractAggregateRoot<A>()
        where A : BaseAggregateRootEntity<A> {
    @CreatedDate
    @Column(nullable = false)
    var createdDate: LocalDateTime? = null
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var updatedDate: LocalDateTime? = null
        protected set
}

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AutoIncrementId<A> protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) : BaseAggregateRootEntity<A>()
        where A : AutoIncrementId<A> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AutoIncrementId<*>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class RandomId<A> protected constructor(
    @Id
    @GenericGenerator(name = "random_id", strategy = "com.brainbackdoor.support.RandomValueIdGenerator")
    @GeneratedValue(generator = "random_id")
    @Column(length = RandomValueIdGenerator.ID_LENGTH, nullable = false)
    val id: String = ""
) : BaseAggregateRootEntity<A>()
        where A : RandomId<A> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RandomId<*>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}