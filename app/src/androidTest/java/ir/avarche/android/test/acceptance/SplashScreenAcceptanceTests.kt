package ir.avarche.android.test.acceptance

import androidx.test.ext.junit.rules.ActivityScenarioRule
import ir.avarche.android.app.MainActivity
import ir.avarche.android.app.database.DatabaseGateway
import ir.avarche.android.test.acceptance.scenarios.SplashScenario
import ir.avarche.android.test.applicationContext
import org.junit.Rule
import org.junit.Test

class SplashScreenAcceptanceTests
{
    init {
        DatabaseGateway.initialize(applicationContext,true)
    }

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
        SplashScenario.supposeUserWithMobileExistsOnLocalCacheAsLoggedInUser("09120000000")

        SplashScenario.supposeUserOpensTheAppAndSeesSplashScreen()

        SplashScenario.ensureThatUserProceedsToMainPage()
    }
}