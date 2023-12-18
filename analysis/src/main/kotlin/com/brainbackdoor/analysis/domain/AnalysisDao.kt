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
        "SELECT hobby, ROUND((Count(id) / (SELECT COUNT(id) FROM `survey`) * 100),1) as 'count'\n" +
                "FROM `survey`\n" +
                "GROUP BY hobby"
    )
    fun findCodingAsHobby(): List<CodingAsHobbyResponse>

    @Select("SELECT P.member_id, `latest_lecture`.name \n" +
        "FROM `participant` AS P\n" +
        "JOIN `latest_lecture` \n" +
        "ON `latest_lecture`.lecture_id = P.lecture_id")
    fun findLecturesByParticipants(): List<LecturesByParticipantsResponse>


    @Select("SELECT S.id, P.name\n" +
        "FROM (\n" +
        "\tSELECT id\n" +
        "\tFROM analysis.survey\n" +
        "    WHERE hobby = 'Yes' AND (dev_type LIKE '%Student%' OR years_coding = '0-2 years')) AS S\n" +
        "JOIN (\n" +
        "\tSELECT participant.id, name  FROM analysis.participant\n" +
        "\tJOIN (SELECT lecture_id, name FROM analysis.latest_lecture) AS L ON L.lecture_id = participant.lecture_id\n" +
        ") AS P ON P.id = S.id\n" +
        "WHERE S.id >= #{io}\n" +
        "LIMIT 0, 10")
    fun findLectureNameOrderSurveyId(id: Long): List<LectureNameOrderSurveyIdResponse>


    @Select("SELECT \n" +
        "operating_system\n" +
        ",COUNT(S.id) AS `count`\n" +
        "FROM (SELECT member_id FROM analysis.member WHERE age BETWEEN 30 AND 39) AS M\n" +
        "INNER JOIN (SELECT member_id, survey_id, lecture_id FROM analysis.participant) AS P\n" +
        "ON P.member_id = M.member_id\n" +
        "INNER JOIN (SELECT id, operating_system FROM analysis.survey) AS S\n" +
        "ON P.survey_id = S.id\n" +
        "INNER JOIN (SELECT lecture_id FROM analysis.latest_lecture WHERE name = '인프라공방') as B\n" +
        "ON P.lecture_id = B.lecture_id\n" +
        "GROUP BY operating_system")
    fun findMemberByInfraWorkshop(): List<MemberByInfraWorkshopResponse>
}