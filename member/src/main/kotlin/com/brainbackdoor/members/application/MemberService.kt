package com.brainbackdoor.members.application

import com.brainbackdoor.members.domain.*
import com.brainbackdoor.members.ui.MemberCreateRequest
import com.brainbackdoor.members.ui.MemberResponse
import exception.ResourceNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val roleRepository: RoleRepository
) {
    fun create(request: MemberCreateRequest): MemberResponse {
        check(request.consentByMember && request.consentByPrivacy) {
            throw IllegalArgumentException("개인정보 처리방침 혹은 회원 약관에 동의를 하지 않았습니다.")
        }

        val member = create(request.of())
        return MemberResponse(member)
    }

    fun find(targetId: String): MemberResponse {
        val member: Member = findById(targetId)
        return MemberResponse(member)
    }

    fun findById(id: String): Member {
        return memberRepository
            .findById(id)
            .orElseThrow { throw ResourceNotFoundException("$id 사용자가 없습니다.") }
    }

    fun findByEmail(email: String): Member = memberRepository
        .findByEmailAddress(email)
        .orElseThrow { throw ResourceNotFoundException("$email 사용자가 없습니다.") }

    fun existsBy(email: String): Boolean = memberRepository.existsMemberByEmailAddress(email)

    fun checkPassword(email: String, password: String): Member {
        val member = findByEmail(email)
        member.checkPassword(password)
        return member
    }

    fun create(member: Member): Member = memberRepository.save(member)

    private fun MemberCreateRequest.of(): Member =
        Member(
            this.email,
            this.password,
            this.consentByMember,
            this.consentByPrivacy,
            guestRole()
        )

    private fun guestRole(): Role = roleRepository.findByRoleType(RoleType.ROLE_GUEST) ?: Role.guest()
    fun findTest(): List<MemberResponse> {
        return memberRepository.findAll().map { MemberResponse(it) }
    }


}