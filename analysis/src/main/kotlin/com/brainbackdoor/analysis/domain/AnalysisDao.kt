package com.brainbackdoor.analysis.domain

import com.brainbackdoor.analysis.web.CodingAsHobbyResponse
import com.brainbackdoor.analysis.web.LectureNameOrderSurveyIdResponse
import com.brainbackdoor.analysis.web.LecturesByParticipantsResponse
import com.brainbackdoor.analysis.web.MemberByInfraWorkshopResponse
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface AnalysisDao {

    @Select(
        "SELECT hobby, ROUND((Count(id) / (SELECT COUNT(id) FROM SURVEY) * 100),1) as 'count'\n" +
                "FROM SURVEY\n" +
                "GROUP BY hobby"
    )
    fun findCodingAsHobby(): List<CodingAsHobbyResponse>

    @Select("SELECT P.id, LECTURE.name \n" +
        "FROM PARTICIPANT AS P\n" +
        "JOIN LECTURE \n" +
        "ON LECTURE.lecture_id = P.lecture_id")
    fun findLecturesByParticipants(): List<LecturesByParticipantsResponse>

    fun findLectureNameOrderSurveyId(id: Long): List<LectureNameOrderSurveyIdResponse>

    fun findMemberByInfraWorkshop(): List<MemberByInfraWorkshopResponse>
}