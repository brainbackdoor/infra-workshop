package com.brainbackdoor.support

import com.brainbackdoor.members.domain.*
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

class InitialTestData : InitialData() {
    override fun loadSpecificData() {
        createUser()
    }

    private fun createUser() {
        val roles = mutableListOf<Role>()
        roles.add(roleRepository.findByRoleType(RoleType.ROLE_ADMIN)!!)

        val admin = Member(
            email(ADMIN_EMAIL),
            Password(ADMIN_PASSWORD),
            consentByMember = true,
            consentByPrivacy = true,
            roles = roles
        )

        if (!memberService.existsBy(ADMIN_EMAIL)) {
            memberService.create(admin)
        }
    }

    companion object {
        const val ADMIN_EMAIL = "test@gmail.com"
        const val ADMIN_PASSWORD = "password1!"
        var ADMIN_TOKEN = ""
    }
}


