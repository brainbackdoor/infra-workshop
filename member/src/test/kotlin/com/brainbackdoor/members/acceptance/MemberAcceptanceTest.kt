package com.brainbackdoor.members.acceptance

import com.brainbackdoor.support.AcceptanceTest
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_PASSWORD
import org.junit.jupiter.api.Test

class MemberAcceptanceTest: AcceptanceTest() {
    @Test
    fun `회원가입을 한다`() {
        val mail = "sample@gmail.com"
        val createResponse = 회원_생성을_요청(mail, ADMIN_PASSWORD)
        회원_생성됨(createResponse)

        val findResponse = 회원_정보_조회_요청(createResponse)
        회원_정보_조회됨(findResponse, mail)
    }
}