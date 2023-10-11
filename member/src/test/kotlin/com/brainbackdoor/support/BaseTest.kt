package com.brainbackdoor.support

import com.brainbackdoor.auth.application.AuthService
import com.brainbackdoor.auth.ui.LoginRequest
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_EMAIL
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_PASSWORD
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_TOKEN
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@ActiveProfiles(value = ["test"])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class BaseTest protected constructor()

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest : BaseTest() {
    @LocalServerPort
    var port = 0

    @Autowired
    private lateinit var databaseCleanup: DatabaseCleanup

    @Autowired
    private lateinit var testData: InitialTestData

    @Autowired
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port
            databaseCleanup.afterPropertiesSet()
        }

        databaseCleanup.execute()
        testData.loadSpecificData()

        ADMIN_TOKEN = authService.login(LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD)).accessToken
    }
}