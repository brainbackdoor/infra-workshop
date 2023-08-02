package members.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.naming.AuthenticationException

class PasswordTest {
    @Test
    fun `Password check`() {
        val target = "password1!"
        val password = Password(target)

        assertThat(password.check(target)).isTrue()
        assertThrows<AuthenticationException> { password.check("other") }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123", "password1!password1!a"])
    fun `패스워드는 8자 이상, 20자 이하여야 한다`(password: String) {
        assertThrows<IllegalArgumentException> { Password(password) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["password", "1p!", "password1", "password!", "1234567!", "12345678", "!@#$%^&*"])
    fun `하나 이상의 숫자 및 소문자와 특수 문자를 모두 포함해야 한다`(password: String) {
        assertThrows<IllegalArgumentException> { Password(password) }
        org.junit.jupiter.api.assertDoesNotThrow { Password("password1!") }
    }
}