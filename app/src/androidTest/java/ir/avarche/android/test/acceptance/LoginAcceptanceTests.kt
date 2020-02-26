package ir.avarche.android.test.acceptance

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.avarche.android.app.MainActivity
import ir.avarche.android.test.acceptance.scenarios.LoginScenario
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginAcceptanceTests {

    @get:Rule
    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun invalidNumberScenario()
    {
        LoginScenario.userEntersMobileAndClicksLogin("")

        LoginScenario.userIsPromptedWithInvalidNumberWarning()
    }

    @Test
    fun uponEnteringValidMobileUserWillProceedToVerificationPageScenario()
    {
        LoginScenario.supposeUserWithMobileExistsOnServer("09120000000")

        LoginScenario.userEntersMobileAndClicksLogin("09120000000")

        LoginScenario.userWillBeProceededToCodeVerificationPage()
    }

    @Test
    fun userEntersValidMobileAndVerifiesTheWrongCodeScenario()
    {
        LoginScenario.supposeUserWithMobileExistsOnServer("09120000000")
        LoginScenario.supposeVerificationCodeIs("code")

        LoginScenario.userEntersMobileAndClicksLogin("09120000000")

        LoginScenario.userWillBeProceededToCodeVerificationPage()

        LoginScenario.userEntersVerificationCodeAndAsksToConfirmIt("wrong code")

        LoginScenario.userIsPromptedWithInvalidCode()
    }


    @Test
    fun userEntersValidMobileAndVerifiesTheCorrectCodeScenario()
    {
        LoginScenario.supposeUserWithMobileExistsOnServer("09120000000")
        LoginScenario.supposeVerificationCodeIs("code")

        LoginScenario.userEntersMobileAndClicksLogin("09120000000")

        LoginScenario.userWillBeProceededToCodeVerificationPage()

        LoginScenario.userEntersVerificationCodeAndAsksToConfirmIt("code")

        LoginScenario.userIsPromptedWithCongratulationDialog()
    }
}