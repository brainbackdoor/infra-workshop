package com.brainbackdoor.support

import com.brainbackdoor.members.application.MemberService
import com.brainbackdoor.members.domain.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Transactional
@Component
abstract class InitialData {
    @Autowired
    lateinit var roleRepository: RoleRepository


    @Autowired
    lateinit var memberService: MemberService

    abstract fun loadSpecificData()

    private fun createRoles() {
        RoleType.values().forEach { roleType ->
            val role = roleRepository.findByRoleType(roleType)
            role ?: roleRepository.save(roleType.createRole())
        }
    }

    fun load() {
        createRoles()
        loadSpecificData()
    }
}

class ProdInitialData : InitialData() {
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
        const val ADMIN_EMAIL = "brainbackdoor@gmail.com"
        const val ADMIN_PASSWORD = "password1!"
    }
}

@Component
class InitialDataBootStrap(
    private val initialData: InitialData
) : ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        initialData.load()
    }
}

@Configuration
class DataLoader {
    @Bean
    @Profile("local", "prod")
    fun prodData(): InitialData {
        return ProdInitialData()
    }
}
