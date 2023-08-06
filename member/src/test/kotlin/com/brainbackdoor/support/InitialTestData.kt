package com.brainbackdoor.support

import com.brainbackdoor.members.domain.Member
import com.brainbackdoor.members.domain.Password
import com.brainbackdoor.members.domain.mail
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@Configuration
class TestDataLoader {

    @Bean
    @Profile("test")
    fun testData(): InitialData {
        return InitialTestData()
    }
}
open class InitialTestData : InitialData() {
    override fun loadSpecificData() {
        createUser()
    }

    private fun createUser() {
        val admin = Member(
            mail(ADMIN_EMAIL),
            Password(ADMIN_PASSWORD),
            consentByMember = true,
            consentByPrivacy = true
        )

        if (!memberService.existsBy(ADMIN_EMAIL)) {
            memberService.create(admin)
        }
    }

    companion object {
        const val ADMIN_EMAIL = "test@gmail.com"
        const val ADMIN_PASSWORD = "password1!"
    }

}


