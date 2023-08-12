package com.brainbackdoor.web.auth.external

import com.brainbackdoor.web.auth.LoginMember
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "login-member-client",
    url = "\${external.login-member.host}"
)
interface LoginMemberClient {
    @GetMapping(value = ["/api/auth/me"])
    fun findMe(
        @RequestHeader("Authorization") token: String
    ): LoginMember
}