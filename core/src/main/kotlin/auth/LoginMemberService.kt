package auth

import auth.external.LoginMemberClient
import org.springframework.stereotype.Component

@Component
class LoginMemberService(
    private val loginMemberClient: LoginMemberClient
) {
    fun findMe(token: String) = loginMemberClient.findMe(BEARER_TOKEN + token)

    companion object {
        const val BEARER_TOKEN = "Bearer "
    }
}