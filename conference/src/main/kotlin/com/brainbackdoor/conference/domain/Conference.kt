package com.brainbackdoor.conference.domain

import domain.RandomId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Conference(

    var area: String,

    var schedule: LocalDateTime,

    @Lob
    var contents: String = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_conference_to_recruitment"))
    var recruitment: Recruitment

) : RandomId<Conference>() {

}
