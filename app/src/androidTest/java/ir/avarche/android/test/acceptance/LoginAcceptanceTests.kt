package ir.avarche.android.test.acceptance

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.avarche.android.app.MainActivity
import ir.avarche.android.app.infrastructure.database.DatabaseGateway
import ir.avarche.android.test.acceptance.scenarios.LoginScenario
import ir.avarche.android.test.applicationContext
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginAcceptanceTests {

    private val validMobile = "09120000000"
    private val correctCode = "code"
    private val wrongCode = "wrong code"

    init {
        DatabaseGateway.initialize(applicationContext,true)
    }

    @get:Rule
    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @After
    fun cleanUp()
    {
        DatabaseGateway.cleanUp()
    }

    @Test
    fun invalidNumberScenario()
    {
        LoginScenario.userEntersMobileAndClicksLogin("")

        LoginScenario.userIsPromptedWithInvalidNumberWarning()
    }

    @Test
    fun uponEnteringValidMobileUserWillProceedToVerificationPageScenario()
    {
        LoginScenario.supposeUserWithMobileExistsOnServer(validMobile)

        LoginScenario.userEntersMobileAndClicksLogin(validMobile)

        LoginScenario.userWillBeProceededToCodeVerificationPage()
    }

    @Test
    fun userEntersValidMobileAndVerifiesTheWrongCodeScenario()
    {
        LoginScenario.supposeUserWithMobileExistsOnServer(validMobile)
        LoginScenario.supposeVerificationCodeIs(correctCode)

        LoginScenario.userEntersMobileAndClicksLogin(validMobile)

        LoginScenario.userWillBeProceededToCodeVerificationPage()

        LoginScenario.userEntersVerificationCodeAndAsksToConfirmIt(wrongCode)

        LoginScenario.userIsPromptedWithInvalidCode()
    }


    @Test
    fun userEntersValidMobileAndVerifiesTheCorrectCodeScenario()
    {
        LoginScenario.supposeUserWithMobileExistsOnServer(validMobile)
        LoginScenario.supposeVerificationCodeIs(correctCode)

        LoginScenario.userEntersMobileAndClicksLogin(validMobile)

        LoginScenario.userWillBeProceededToCodeVerificationPage()

        LoginScenario.userEntersVerificationCodeAndAsksToConfirmIt(correctCode)

        LoginScenario.userIsMovedToWelcomePage()

        LoginScenario.userIsSavedAsLoggedIn(validMobile)
    }

    @Test
    fun userLoginSuccessfullyAndThenLogsOutScenario()
    {
        LoginScenario.performLoginHappyScenario(validMobile,correctCode)

        LoginScenario.userOpensSideDrawerAndPressesLogout()

        LoginScenario.userWillBeProceededToEnteringMobilePage()
    }
}