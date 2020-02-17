package ir.avarche.android.test.acceptance

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.avarche.android.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSendsMobileSuccessfullyAndProceedsToVerificationCodePage {

    @get:Rule
    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun userIsAskedToEnterLoginMobile()
    {
        ensureThatViewIsOnScreenNow(R.id.mobileField)
    }

    @Test
    fun userEntersNothingAndClicksLoginAndIsPromptedWithWarningThatMobileIsEmpty()
    {
        typeTextOnTextField(R.id.mobileField,"")

        clickOnView(R.id.askForVerificationCodeButton)

        ensureThatViewWithTextIsOnScreenNow(getString(R.string.warning_mobile_should_not_be_empty))
    }


}