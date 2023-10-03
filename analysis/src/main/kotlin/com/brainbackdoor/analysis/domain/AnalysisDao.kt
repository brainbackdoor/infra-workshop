package com.brainbackdoor.analysis.domain

import com.brainbackdoor.analysis.ui.CodingAsHobbyResponse
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface AnalysisDao {

    @Select("SELECT hobby, ROUND((Count(id) / (SELECT COUNT(id) FROM analysis.survey) * 100),1) as 'count'\n" +
            "FROM analysis.survey\n" +
            "GROUP BY hobby")
    fun findCodingAsHobby(): CodingAsHobbyResponse?
}