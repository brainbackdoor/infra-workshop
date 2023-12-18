package com.brainbackdoor.analysis

import com.brainbackdoor.infra.AsyncService
import com.brainbackdoor.members.MemberService
import com.brainbackdoor.support.joinAsync
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class AnalysisMemberService(
    private val analysisClient: AnalysisClient,
    private val memberService: MemberService,
    private val asyncService: AsyncService
) {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    fun findLecturesByParticipantsEmail(loginToken: String): List<LecturesByParticipantEmailResponse> {
        val lectures = analysisClient.findLecturesByParticipants(secretKey)
        val members = memberService.findAll(loginToken)

        return lectures.stream()
            .map { LecturesByParticipantEmailResponse(members, it) }
            .toList()
    }

    // 비동기처리로 대응할수도 있지만, 이 경우엔 회원 정보가 너무 많아 네트워크 비용이 많이 발생합니다. 이 경우엔, 어떻게 대응하는 것이 합리적일까요?
    fun findLecturesByParticipantsEmailAsync(loginToken: String): List<LecturesByParticipantEmailResponse> {
        val lectures = async { analysisClient.findLecturesByParticipants(secretKey) }
        val members = async { memberService.findAll(loginToken) }

        joinAsync(lectures, members)

        return lectures.get().stream()
            .map { LecturesByParticipantEmailResponse(members.get(), it) }
            .toList()
    }

    private fun <T> async(supplier: () -> T): CompletableFuture<T> = asyncService.supplyAsync(supplier)

    companion object {
        const val ANONYMOUS_EMAIL = "anonymous@gmail.com"
    }
}