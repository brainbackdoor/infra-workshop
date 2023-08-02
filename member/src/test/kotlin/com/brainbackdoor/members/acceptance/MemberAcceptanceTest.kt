package com.brainbackdoor.members.acceptance

import com.brainbackdoor.AcceptanceTest
import com.brainbackdoor.members.domain.MemberTest.Companion.MAIL
import com.brainbackdoor.members.domain.MemberTest.Companion.PASSWORD
import org.junit.jupiter.api.Test

class MemberAcceptanceTest: AcceptanceTest() {
    @Test
    fun `회원가입을 한다`() {
        val createResponse = 회원_생성을_요청(MAIL, PASSWORD)
        회원_생성됨(createResponse)

        val findResponse = 회원_정보_조회_요청(createResponse)
        회원_정보_조회됨(findResponse, MAIL)
    }
}