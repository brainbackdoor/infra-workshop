package members.domain

import jakarta.persistence.Embeddable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.naming.AuthenticationException

@Embeddable
class Password(
    private var password: String
) {
    init {
        check(validateCombination(this.password)) {
            throw IllegalArgumentException("비밀번호는 8자리이상(영문,숫자,특수문자 포함) 20자리 이하로 만드셔야 합니다.")
        }

        this.password = encode(this.password)
    }

    fun check(password: String): Boolean {
        check(ENCODER.matches(password, this.password)) {
            throw AuthenticationException("사용자의 패스워드가 틀립니다!")
        }

        return true
    }

    private fun encode(password: String): String = ENCODER.encode(password)

    private fun validateCombination(password: String): Boolean = PASSWORD_COMBINATION_REGEX.matches(password)

    fun isMatched(value: String): Boolean {
        return ENCODER.matches(value, this.password).not()
    }

    companion object {
        private val PASSWORD_COMBINATION_REGEX =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&.])[A-Za-z\\d\$@\$!%*#?&.]{8,20}\$".toRegex()
        private val ENCODER: PasswordEncoder = BCryptPasswordEncoder()
    }
}
