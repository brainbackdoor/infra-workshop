package com.brainbackdoor.analysis.application

import com.brainbackdoor.analysis.domain.AnalysisDao
import com.brainbackdoor.analysis.ui.CodingAsHobbyResponse
import com.brainbackdoor.exception.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class AnalysisService(
    private val analysisDao: AnalysisDao,
) {

    fun findCodingAsHobby(): CodingAsHobbyResponse = analysisDao
        .findCodingAsHobby()
        ?: throw ResourceNotFoundException()
}