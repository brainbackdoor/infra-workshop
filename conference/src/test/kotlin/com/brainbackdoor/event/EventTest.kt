package com.brainbackdoor.event

import com.brainbackdoor.events.EventController.Companion.convert
import com.brainbackdoor.events.EventRepository.Companion.events
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class EventTest {
    @ParameterizedTest
    @MethodSource("mbti")
    fun `mbti 유형 pick test`(id: String, mbtiId: Long) {
        val event = events().pick(convert(id))
        assertThat(event.id).isEqualTo(mbtiId)
    }

    companion object {
        @JvmStatic
        fun mbti(): Array<Arguments> {
            return arrayOf(
                Arguments.of("abefas", 2),
                Arguments.of("ebafds", 6),
                Arguments.of("zfasdf", 11),
            )
        }
    }
}