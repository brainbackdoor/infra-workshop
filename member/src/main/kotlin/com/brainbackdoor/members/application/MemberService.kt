package com.brainbackdoor.members.application

import com.brainbackdoor.exception.ResourceNotFoundException
import com.brainbackdoor.members.domain.Member
import com.brainbackdoor.members.domain.MemberRepository
import com.brainbackdoor.members.domain.Role
import com.brainbackdoor.members.domain.RoleRepository
import com.brainbackdoor.members.domain.RoleType
import com.brainbackdoor.members.web.MemberCreateRequest
import com.brainbackdoor.members.web.MemberResponse
import com.brainbackdoor.notifications.NotificationService
import com.brainbackdoor.notifications.SendMailEvent
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val roleRepository: RoleRepository,
    private val notificationService: NotificationService,
    // private val eventPublisher: ApplicationEventPublisher
) {
    fun create(request: MemberCreateRequest): MemberResponse {
        check(request.consentByMember && request.consentByPrivacy) {
            throw IllegalArgumentException("개인정보 처리방침 혹은 회원 약관에 동의를 하지 않았습니다.")
        }

        val member = create(request.of())
        notificationService.send(SendMailEvent(request.email, MAIL_SUBJECTS, MAIL_CONTENTS))
        // eventPublisher.publishEvent(SendMailEvent(request.email, MAIL_SUBJECTS, MAIL_CONTENTS))
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

    companion object {
        const val MAIL_SUBJECTS = "인프라공방에 참여하신걸 환영합니다!"
        const val MAIL_CONTENTS = "회원가입이 완료되었습니다"
    }

    private fun MemberCreateRequest.of(): Member =
        Member(
            this.email,
            this.password,
            this.consentByMember,
            this.consentByPrivacy,
            guestRole()
        )

    private fun guestRole(): Role = roleRepository.findByRoleType(RoleType.ROLE_GUEST) ?: Role.guest()
}