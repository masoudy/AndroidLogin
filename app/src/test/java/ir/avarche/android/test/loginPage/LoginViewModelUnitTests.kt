package ir.avarche.android.test.loginPage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.kotlintest.shouldBe
import ir.avarche.android.app.loginPage.LoginViewModel
import ir.avarche.android.app.loginPage.User
import ir.avarche.android.test.doubles.LoginRepositoryMock
import ir.avarche.android.test.doubles.mockLifecycleOwner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class LoginViewModelUnitTests {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val lifecycleOwner = mockLifecycleOwner()
    private val loginRepositoryMock = LoginRepositoryMock()
    private val viewModel = LoginViewModel(loginRepositoryMock)
    private val invalidMobile = "hello"
    private val validMobile = "09120000000"

    @Test
    fun `when user asks login, view model will first check if mobile is a valid number and tell view to show warning`()
    {
        viewModel.mobile = invalidMobile

        viewModel.login()

        viewModel.invalidMobileWarns.totalPublishedEvents shouldBe 1
    }

    @Test
    fun `when user asks login with acceptable number, view model asks repository to login`()
    {
        viewModel.mobile = validMobile

        viewModel.login()

        loginRepositoryMock.ensureThatWasAskedToLoginWithMobile(validMobile)
    }

    @Test
    fun `calling back verification code was sent when login call succeeds`()
    {
        loginRepositoryMock.nextCallToLoginReturnBody = User(validMobile)
        viewModel.mobile = validMobile

        val verificationCodeWasSent = mutableListOf<Boolean>()

        viewModel.verificationCodeSent.handleIfHasNotBeenHandled(lifecycleOwner) {
            verificationCodeWasSent.add(it)
        }

        viewModel.login()

        verificationCodeWasSent shouldBe listOf(false , true)
    }

    @Test
    fun `when view asks view model to login, it enables inProgress status and after getting result it resets the inProgress status`()
    {
        viewModel.mobile = validMobile
        viewModel.isInProgress.value shouldBe false

        var hasChangedToTrue = false
        var thenChangedToFalse = false

        viewModel.isInProgress.observeForever {
            if(it)
                hasChangedToTrue = true

            if(it.not() && hasChangedToTrue)
                thenChangedToFalse = true

        }

        viewModel.login()

        hasChangedToTrue shouldBe true
        thenChangedToFalse shouldBe true
        viewModel.isInProgress.value shouldBe false
    }


    @Test
    fun `when calling verifyCode succeeds it will publish login event`()
    {
        loginRepositoryMock.nextCallToLoginVerifyReturnBody = true
        viewModel.mobile = validMobile
        viewModel.verificationCode = "code"

        viewModel.isLoggedIn.value shouldBe false

        viewModel.verifyCode()

        loginRepositoryMock.ensureThatWasAskedToVerifyCode(validMobile,"code")
        viewModel.isLoggedIn.value shouldBe true
    }

    @Test
    fun `verifying wrong code will publish event`()
    {
        loginRepositoryMock.nextCallToLoginVerifyReturnBody = false

        viewModel.verifyCode()

        viewModel.wrongCodeWarns.totalPublishedEvents shouldBe 1
    }
}