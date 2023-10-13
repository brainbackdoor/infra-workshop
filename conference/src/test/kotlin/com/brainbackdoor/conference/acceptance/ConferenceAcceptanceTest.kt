package com.brainbackdoor.conference.acceptance

import com.brainbackdoor.conferences.web.ConferenceRequest
import com.brainbackdoor.support.AcceptanceTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value

class ConferenceAcceptanceTest : AcceptanceTest() {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    @Test
    fun `컨퍼런스를 생성한다`() {
        val param = ConferenceRequest(
            "2123-10-01T00:00",
            "2123-10-05T23:59",
            "2123-10-10T12:00",
            "서울",
            10,
            100_000,
        )

        val response = 컨퍼런스_생성_요청(secretKey, param)
        컨퍼런스_생성됨(response)
    }
}