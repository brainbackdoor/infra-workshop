package com.brainbackdoor.web.auth.external

data class MemberCreateRequest(
    val mail: String,
    val password: String,
    val consentByMember: Boolean,
    val consentByPrivacy: Boolean
)

data class MemberResponse(
    val id: String,
    val mail: String,
) {}