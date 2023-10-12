package com.brainbackdoor.conferences.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class Applicants(
    var limited: Int = 0,

    var lotteryBoundary: Int = 0,

    @OneToMany(mappedBy = "recruitment", cascade = [CascadeType.MERGE])
    val applicants: MutableList<Applicant> = mutableListOf()
) {
    fun join(applicant: Applicant) {
        check(applicants.size < limited) {
            throw IllegalArgumentException("모집 정원이 차서 신청할 수 없습니다.")
        }

        check(!contains(applicant)) {
            throw IllegalArgumentException("신청자가 하나의 모집에 두번 신청할 수 없습니다.")
        }

        applicants.add(applicant)
    }

    fun leave(applicant: Applicant) {
        check(contains(applicant)) {
            throw IllegalArgumentException("등록된 신청자가 아닙니다.")
        }

        applicants.remove(applicant)
    }

    fun contains(applicant: Applicant): Boolean = applicants.contains(applicant)

    fun isFulled(): Boolean = applicants.size >= limited
    fun lottery() {
        applicants.shuffle()
        applicants.subList(0, lotteryBoundary)
    }
}