package ir.avarche.android.test.acceptance

import androidx.test.ext.junit.rules.ActivityScenarioRule
import ir.avarche.android.app.MainActivity
import ir.avarche.android.test.acceptance.scenarios.SplashScenario
import org.junit.Rule
import org.junit.Test

class SplashScreenAcceptanceTests
{
    @get:Rule
    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun splashScreenAsksIfUserIsLoggedInAndProceedsToLoginPageIfNot()
    {
        SplashScenario.supposeThatThereIsNoLoggedInUserInCache()

        SplashScenario.supposeUserOpensTheAppAndSeesSplashScreen()

        SplashScenario.ensureThatUserProceedsToLoginPage()
    }

    @Test
    fun splashScreenAsksIfUserIsLoggedInAndProceedsToMainMenuIfItIs()
    {
        SplashScenario.supposeThatThereIsNoLoggedInUserInCache()

        SplashScenario.supposeUserOpensTheAppAndSeesSplashScreen()

        SplashScenario.ensureThatUserProceedsToMainPage()
    }
}