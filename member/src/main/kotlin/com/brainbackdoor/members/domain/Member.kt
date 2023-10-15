package com.brainbackdoor.members.domain

import com.brainbackdoor.domain.RandomId
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany

@Entity
class Member(
    @Embedded
    var email: Email,

    @Embedded
    var password: Password?,

    var consentByMember: Boolean = false,

    var consentByPrivacy: Boolean = false,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "member_roles",
        joinColumns = [
            JoinColumn(
                name = "member_id", referencedColumnName = "id",
                foreignKey = ForeignKey(name = "fk_member_roles_to_member")
            )
        ],
        inverseJoinColumns = [
            JoinColumn(
                name = "role_id", referencedColumnName = "id",
                foreignKey = ForeignKey(name = "fk_member_roles_to_role")
            )
        ]
    )
    val roles: MutableList<Role>
) : RandomId<Member>() {

    constructor(
        email: String,
        password: String,
        consentByMember: Boolean,
        consentByPrivacy: Boolean,
        role: Role
    ) : this(
        email(email),
        Password(password),
        consentByMember,
        consentByPrivacy,
        mutableListOf(role)
    )

    init {
        password
            ?.isMatched(this.email.address)
            ?.let {
                check(it) { throw IllegalArgumentException("이용자 ID를 이용한 패스워드는 사용할 수 없습니다.") }
            }
    }

    fun checkPassword(password: String) {
        this.password?.check(password)
    }

    fun email(): String = email.address

    fun maskedEmail(): String = email.masking()

    @Embeddable
    class Email(
        @Column(unique = true, length = 100)
        val address: String
    ) {
        init {
            if (!checkMailFormat(this.address)) {
                throw IllegalArgumentException("이메일 형식이 유효하지 않습니다")
            }
        }

        fun masking(): String = address.replace(EMAIL_MASKING_REGEX) {
            it.groupValues[1] +
                "*".repeat(it.groupValues[2].length) +
                it.groupValues[3] +
                "*".repeat(it.groupValues[4].length)
        }

        private fun checkMailFormat(address: String): Boolean = EMAIL_REGEX.matches(address)

        companion object {
            private val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}\$".toRegex()
            private val EMAIL_MASKING_REGEX = """^([^@]{2})([^@]+)([^@]{0}@)([^@]{4})""".toRegex()
        }
    }
}

fun email(address: String): Member.Email = Member.Email(address)