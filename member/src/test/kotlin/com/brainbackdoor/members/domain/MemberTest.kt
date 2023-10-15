package com.brainbackdoor.members.domain

import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_EMAIL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MemberTest {
    @ParameterizedTest
    @ValueSource(strings = ["mail", "mail@", "@mail.com", "mail@mail!", "mail@mail.m"])
    fun `이메일 형식 유효성을 검증한다`(mail: String) {
        assertThrows<IllegalArgumentException> { email(mail) }
        assertDoesNotThrow { email(ADMIN_EMAIL) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["email1@email.com"])
    fun `이용자 ID와 동일한 패스워드는 사용할 수 없다`(mail: String) {
        assertThrows<IllegalArgumentException> {
            Member(
                mail,
                mail,
                consentByMember = true,
                consentByPrivacy = true,
                role = Role.guest()
            )
        }
    }

    @Test
    fun `이메일 주소 마스킹을 할 수 있다`() {
        val email = email("mail@gmail.com")
        val expected = "ma**@****l.com"

        assertThat(expected).isEqualTo(email.masking())
    }
}
