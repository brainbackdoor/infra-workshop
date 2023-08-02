package com.brainbackdoor.study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CryptStudyTest {

    /**
     * 시도할 떄마다 Encoding 된 결과는 다르나,
     * encoder로 매칭해보면 패스워드가 같음을 확인할 수 있다.
     */
    @Test
    fun `BCrypt check`() {
        val encoder = BCryptPasswordEncoder()
        val password = "password"

        val encoded1 = encoder.encode(password)
        val encoded2 = encoder.encode(password)
        assertThat(encoded1).isNotEqualTo(encoded2)

        val actual1 = encoder.matches(password, encoded1)
        val actual2 = encoder.matches(password, encoded2)
        assertThat(actual1).isTrue()
        assertThat(actual2).isTrue()
    }
}
