package ir.avarche.android.test.acceptance.scenarios

import ir.avarche.android.app.database.DatabaseGateway
import ir.avarche.android.app.database.User
import ir.avarche.android.test.*
import kotlinx.coroutines.runBlocking

object SplashScenario {

    fun supposeThatThereIsNoLoggedInUserInCache()
    {

    }

    fun supposeUserWithMobileExistsOnLocalCacheAsLoggedInUser(mobile: String) {
        runBlocking { DatabaseGateway.instance.userDao().updateLoggedInUser(User(mobile))}
    }

    fun ensureThatUserProceedsToLoginPage()
    {
        ensureThatViewIsOnScreenNow(R.id.mobileField)
    }

    fun ensureThatUserProceedsToMainPage()
    {
        ensureThatViewIsOnScreenNow(R.id.welcomeText)
    }

    fun supposeUserOpensTheAppAndSeesSplashScreen() {
  //      ensureThatViewIsOnScreenNow(R.id.loadingSplash)
    }

}