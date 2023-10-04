package com.brainbackdoor.analysis.application

import com.brainbackdoor.analysis.domain.AnalysisDao
import com.brainbackdoor.analysis.ui.CodingAsHobbyResponse
import com.brainbackdoor.analysis.ui.LectureNameOrderSurveyIdResponse
import com.brainbackdoor.analysis.ui.LecturesByParticipantsResponse
import com.brainbackdoor.analysis.ui.MemberByInfraWorkshopResponse
import org.springframework.stereotype.Service

@Service
class AnalysisService(
    private val analysisDao: AnalysisDao,
) {

    fun findCodingAsHobby(): List<CodingAsHobbyResponse> =
        analysisDao.findCodingAsHobby()

    fun findLecturesByParticipants(): List<LecturesByParticipantsResponse> =
        analysisDao.findLecturesByParticipants()

    fun findLectureNameOrderSurveyId(id: Long): List<LectureNameOrderSurveyIdResponse> =
        analysisDao.findLectureNameOrderSurveyId(id)

    fun findMemberByInfraWorkshop(): List<MemberByInfraWorkshopResponse> =
        analysisDao.findMemberByInfraWorkshop()

}