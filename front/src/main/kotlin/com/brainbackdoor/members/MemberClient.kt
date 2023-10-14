package com.brainbackdoor.members

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "member-client",
    url = "\${external.login-member.host}"
)
interface MemberClient {
    @GetMapping(value = ["/api/members"])
    fun findAll(
        @RequestHeader("Authorization") token: String
    ): List<MemberResponse>
}