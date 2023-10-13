package com.brainbackdoor.conference.acceptance

import com.brainbackdoor.conferences.domain.RecruitmentStatus
import com.brainbackdoor.conferences.web.*
import com.brainbackdoor.support.AcceptanceTest
import io.restassured.common.mapper.TypeRef
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus

class ConferenceAcceptanceTest : AcceptanceTest() {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    @Test
    fun `컨퍼런스를 생성한다`() {
        val response = 컨퍼런스_생성_요청(secretKey, param)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    @Test
    fun `유효한 secret-key 없이는 컨퍼런스를 생성할 수 없다`() {
        val response = 컨퍼런스_생성_요청("none", param)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    fun `컨퍼런스 전체 리스트를 조회할 수 있다`() {
        val areas = arrayOf("서울", "제주도")
        for (area: String in areas) {
            val param = ConferenceRequest(start, end, schedule, area, limited, fee)
            컨퍼런스_생성_요청(secretKey, param)
        }

        val response = 컨퍼런스_전체_리스트_조회_요청(secretKey).`as`(object : TypeRef<List<ConferenceAllResponse>>() {})
        assertThat(response.size).isEqualTo(areas.size)
    }

    @Test
    fun `지역별 컨퍼런스를 조회할 수 있다`() {
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "부산", limited, fee, "contents 1"))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "부산", limited, fee, "contents 2"))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "대구", limited, fee))

        val busan = 컨퍼런스_리스트_조회_요청(secretKey, area = "부산").`as`(object : TypeRef<List<ConferenceResponse>>() {})
        assertThat(busan.size).isEqualTo(2)
        assertThat(busan.map { it.area }).contains("부산")
        assertThat(busan.map { it.area }).isNotIn("대구")

        val daegu = 컨퍼런스_리스트_조회_요청(secretKey, area = "대구").`as`(object : TypeRef<List<ConferenceResponse>>() {})
        assertThat(daegu.size).isEqualTo(1)
        assertThat(daegu.map { it.area }).isNotIn("부산")
        assertThat(daegu.map { it.area }).contains("대구")
    }

    @Test
    fun `일자별 컨퍼런스를 조회할 수 있다`() {
        val schedule = "2123-10-19T23:59"
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, "2123-10-18T15:00", "서울", limited, fee))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "서울", limited, fee))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "부산", limited, fee))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "대구", limited, fee))

        val response = 컨퍼런스_리스트_조회_요청(secretKey, schedule = schedule)
            .`as`(object : TypeRef<List<ConferenceResponse>>() {})

        assertThat(response.size).isEqualTo(3)
    }

    @Test
    fun `지역, 일자별 컨퍼런스를 조회할 수 있다`() {
        val schedule = "2123-10-19T23:59"
        val area = "부산"

        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, "2123-10-18T15:00", area, limited, fee))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "서울", limited, fee))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, area, limited, fee))
        컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, "대구", limited, fee))

        val response = 컨퍼런스_리스트_조회_요청(secretKey, schedule = schedule, area = area)
            .`as`(object : TypeRef<List<ConferenceResponse>>() {})

        assertThat(response.size).isEqualTo(1)
    }

    @Test
    fun `컨퍼런스 전체 일정 조회시 일정 오름차순, 모집기간 마지막일 오름차순, 지역 오름차순으로 정렬된다`() {
        val first = ConferenceRequest(start, "2123-10-05T23:59", "2123-10-18T15:00", "서울", limited, fee, "contents 1")
        val second = ConferenceRequest(start, "2123-10-05T15:00", "2123-10-19T23:59", "서울", limited, fee, "contents 2")
        val third = ConferenceRequest(start, "2123-10-06T15:00", "2123-10-19T23:59", "서울", limited, fee)
        val fourth = ConferenceRequest(start, "2123-10-06T15:00", "2123-10-19T23:59", "포항", limited, fee)
        컨퍼런스_생성_요청(secretKey, first)
        컨퍼런스_생성_요청(secretKey, second)
        컨퍼런스_생성_요청(secretKey, third)
        컨퍼런스_생성_요청(secretKey, fourth)

        val response = 컨퍼런스_전체_리스트_조회_요청(secretKey).`as`(object : TypeRef<List<ConferenceAllResponse>>() {})

        assertThat(response.size).isEqualTo(4)
        assertThat(response[0].schedule).isEqualTo(first.conferenceSchedule)
        assertThat(response[1].schedule).isEqualTo(second.conferenceSchedule)
        assertThat(response[2].recruitmentEndDate).isEqualTo(third.recruitmentEndDate)
        assertThat(response[3].area).isEqualTo(fourth.area)
    }

    @Test
    fun `컨퍼런스 모집 상태를 변경할 수 있다`() {
        val id = 컨퍼런스_생성_요청(secretKey, param).id()
        var result = 컨퍼런스_모집_상태_확인_요청(secretKey, id).`as`(ConferenceStatusResponse::class.java).status
        assertThat(result).isEqualTo(RecruitmentStatus.READY.name)

        for (status: RecruitmentStatus in RecruitmentStatus.values()) {
            컨퍼런스_모집_상태_변경_요청(secretKey, id, ConferenceStatusRequest(status.name))
            result = 컨퍼런스_모집_상태_확인_요청(secretKey, id).`as`(ConferenceStatusResponse::class.java).status
            assertThat(result).isEqualTo(status.name)
        }
    }

    @Test
    fun `모집 정원보다 많은 사람이 컨퍼런스를 신청할 수 없다`() {
        // given
        val limited = 1
        val id = 컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, area, limited, fee)).id()
        컨퍼런스_모집_상태_변경_요청(secretKey, id, ConferenceStatusRequest(RecruitmentStatus.START.name))

        컨퍼런스_참가_요청(secretKey, id, ConferenceJoinRequest("member-1"))
        val status = 컨퍼런스_모집_상태_확인_요청(secretKey, id).`as`(ConferenceStatusResponse::class.java).status
        assertThat(status).isEqualTo(RecruitmentStatus.FINISH.name)

        // when
        val response = 컨퍼런스_참가_요청(secretKey, id, ConferenceJoinRequest("other"))

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `특정 컨퍼런스를 수정할 수 있다`() {
        val expected = "2123-10-05T23:59"
        val update = ConferenceRequest(start, expected, schedule, area, limited, fee)
        val id = 컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, area, limited, fee)).id()

        val response = 컨퍼런스_수정_요청(secretKey, id, update).`as`(ConferenceResponse::class.java)

        assertThat(response.recruitmentEndDate).isNotSameAs(end)
        assertThat(response.recruitmentEndDate).isEqualTo(expected)
        assertThat(response.area).isEqualTo(area)
    }

    @Test
    fun `특정 컨퍼런스를 삭제할 수 있다`() {
        val id = 컨퍼런스_생성_요청(secretKey, ConferenceRequest(start, end, schedule, area, limited, fee)).id()

        컨퍼런스_삭제_요청(secretKey, id)

        val response = 특정_컨퍼런스_조회_요청(secretKey, id)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    private fun ExtractableResponse<Response>.id(): String = this.header("Location").split("/")[3]

    private val start = "2123-10-01T00:00"
    private val end = "2123-10-05T23:59"
    private val schedule = "2123-10-10T12:00"
    private val area = "서울"
    private val limited = 10
    private val fee = 100_000
    private val param = ConferenceRequest(start, end, schedule, area, limited, fee)
}