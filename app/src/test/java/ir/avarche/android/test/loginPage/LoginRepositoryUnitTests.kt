package ir.avarche.android.test.loginPage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.kotlintest.shouldBe
import ir.avarche.android.app.loginPage.LoginRepo
import ir.avarche.android.test.doubles.LoginHttpGatewayMock
import ir.avarche.android.test.doubles.UserDaoMock
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginRepositoryUnitTests {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val userDao = UserDaoMock()
    val loginHttpGateway = LoginHttpGatewayMock()
    private val repo =
        LoginRepo(loginHttpGateway, userDao)


    @Test
    fun `when verify succeeds, it will cache user as logged in`()  = runBlocking{
        loginHttpGateway.nextCallToVerifyLogin = true
        var isThere = false
        repo.isThereAnyLoggedInUser.observeForever {
            isThere = it
        }

        repo.verifyCode("123","456")

        isThere shouldBe true
        repo.isThereAnyLoggedInUser.value shouldBe true
        userDao.all().value!!.first().mobile shouldBe "123"
    }




}