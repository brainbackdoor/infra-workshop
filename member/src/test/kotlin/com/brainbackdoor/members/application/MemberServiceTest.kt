package com.brainbackdoor.members.application

import com.brainbackdoor.members.ui.MemberCreateRequest
import com.brainbackdoor.support.AcceptanceTest
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_EMAIL
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_PASSWORD
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired

class MemberServiceTest : AcceptanceTest() {
    @Autowired
    lateinit var memberService: MemberService

    @ParameterizedTest
    @MethodSource("consentForJoinMember")
    fun `개인정보 처리방침 혹은 회원약관에 동의하지 않으면 회원가입을 할 수 없다`(failRequest: MemberCreateRequest) {
        assertThrows<IllegalArgumentException> { memberService.create(failRequest) }

        assertDoesNotThrow {
            memberService.create(
                MemberCreateRequest(
                    "sample@gmail.com",
                    "password1!",
                    consentByMember = true,
                    consentByPrivacy = true
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun consentForJoinMember(): Array<Arguments> {
            return arrayOf(
                Arguments.of(
                    MemberCreateRequest(
                        ADMIN_EMAIL,
                        ADMIN_PASSWORD,
                        consentByMember = true,
                        consentByPrivacy = false
                    )
                ),
                Arguments.of(
                    MemberCreateRequest(
                        ADMIN_EMAIL,
                        ADMIN_PASSWORD,
                        consentByMember = false,
                        consentByPrivacy = true
                    )
                ),
                Arguments.of(
                    MemberCreateRequest(
                        ADMIN_EMAIL,
                        ADMIN_PASSWORD,
                        consentByMember = false,
                        consentByPrivacy = false
                    )
                ),
            )
        }
    }
}