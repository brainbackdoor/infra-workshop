package com.brainbackdoor.analysis

import com.brainbackdoor.exception.ResourceNotFoundException
import com.brainbackdoor.infra.AsyncService
import com.brainbackdoor.members.MemberClient
import com.brainbackdoor.members.MemberResponse
import com.brainbackdoor.support.joinAsync
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class AnalysisMemberService(
    private val analysisClient: AnalysisClient,
    private val memberClient: MemberClient,
    private val asyncService: AsyncService
) {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    fun findLecturesByParticipantsEmail(): List<LecturesByParticipantEmailResponse> {
        val lectures = analysisClient.findLecturesByParticipants()
        val members = memberClient.findAll(secretKey)

        return lectures.stream()
            .map {
                LecturesByParticipantEmailResponse(
                    member(members, it),
                    it.name
                )
            }.toList()
    }

    fun findLecturesByParticipantsEmailAsync(): List<LecturesByParticipantEmailResponse> {
        val lectures = async { analysisClient.findLecturesByParticipants() }
        val members = async { memberClient.findAll(secretKey) }

        joinAsync(lectures, members)

        return lectures.get().stream()
            .map {
                LecturesByParticipantEmailResponse(
                    member(members.get(), it),
                    it.name
                )
            }.toList()
    }

    private fun member(members: List<MemberResponse>, participant: LecturesByParticipantResponse): String =
        members.find { it.id == participant.memberId }
            ?.email
            ?: throw ResourceNotFoundException("${participant.memberId} 참여자 정보가 회원 플랫폼에 존재하지 않습니다.")

    private fun <T> async(supplier: () -> T): CompletableFuture<T> =
        asyncService.supplyAsync(supplier)
}