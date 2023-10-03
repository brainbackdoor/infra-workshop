package com.brainbackdoor.analysis.domain

import com.brainbackdoor.domain.RandomId
import jakarta.persistence.Entity

@Entity
class Survey(
    var hobby: String,
    var devType: String,
    var yearsCoding: String,
    var employment: String,
    var companySize: String,
    var ide: String,
    var operatingSystem: String,
    var versionControl: String,
) : RandomId<Survey>()