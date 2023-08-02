package com.brainbackdoor.members.application

import jakarta.transaction.Transactional
import com.brainbackdoor.members.domain.Member
import com.brainbackdoor.members.domain.MemberRepository
import com.brainbackdoor.members.ui.MemberCreateRequest
import com.brainbackdoor.members.ui.MemberResponse
import org.springframework.stereotype.Service


@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun create(request: MemberCreateRequest): MemberResponse {
        check(request.consentByMember && request.consentByPrivacy) {
            throw IllegalArgumentException("개인정보 처리방침 혹은 회원 약관에 동의를 하지 않았습니다.")
        }

        val member = create(request.of())
        return MemberResponse(member)
    }

    private fun create(member: Member): Member = memberRepository.save(member)

    private fun MemberCreateRequest.of(): Member =
        Member(
            this.mail,
            this.password,
            this.consentByMember,
            this.consentByPrivacy
        )
}