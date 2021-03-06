package ir.avarche.android.test.acceptance.scenarios

import io.kotlintest.shouldBe
import ir.avarche.android.app.infrastructure.CallWasIntercepted
import ir.avarche.android.app.infrastructure.ServerGateway
import ir.avarche.android.app.infrastructure.ServerInterceptor
import ir.avarche.android.app.infrastructure.database.DatabaseGateway
import ir.avarche.android.test.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType

object LoginScenario {

    fun userEntersMobileAndClicksLogin(mobile:String)
    {
        ensureThatViewIsOnScreenNow(R.id.mobileField)

        typeTextOnTextField(R.id.mobileField,mobile)

        clickOnView(R.id.askForVerificationCodeButton)
    }

    fun userIsPromptedWithInvalidNumberWarning()
    {
        ensureThatViewWithTextIsOnScreenNow(getString(R.string.warning_mobile_should_be_correct))
    }

    fun userWillBeProceededToCodeVerificationPage() {
        ensureThatViewIsOnScreenNow(R.id.confirmVerificationCodeButton)
    }

    fun supposeUserWithMobileExistsOnServer(mobile: String) {

        ServerGateway.addInterceptor(object :
            ServerInterceptor {
            override fun intercept(request: Request) {
                if (request.url.encodedPathSegments == listOf("login")
                    &&
                    request.header("mobile") == mobile
                ) {
                    val response = Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(
                            ResponseBody.create(
                                "application/json".toMediaType(), """
                        {"mobile":"$mobile"}
                    """.trimIndent()
                            )
                        )
                        .build()

                    throw CallWasIntercepted(
                        response
                    )
                }
            }
        })

    }

    fun userEntersVerificationCodeAndAsksToConfirmIt(code: String) {
        typeTextOnTextField(R.id.verificationField,code)
        clickOnView(R.id.confirmVerificationCodeButton)
    }

    fun userIsPromptedWithInvalidCode() {
        ensureThatViewWithTextIsOnScreenNow(getString(R.string.warning_verification_code_should_be_correct))
    }

    fun userIsMovedToWelcomePage() {
        ensureThatViewIsOnScreenNow(R.id.welcomeText)
    }

    fun supposeVerificationCodeIs(code: String) {
        ServerGateway.addInterceptor(object :
            ServerInterceptor {
            override fun intercept(request: Request) {
                println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+request.header("code"))

                if (request.url.encodedPathSegments == listOf("verify"))
                {
                    val response = Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(
                            ResponseBody.create(
                                "application/json".toMediaType(), """
                        ${request.header("code") == code}
                    """.trimIndent()
                            )
                        )
                        .build()

                    throw CallWasIntercepted(
                        response
                    )
                }
            }
        })
    }


    fun performLoginHappyScenario(mobile: String,verificationCode:String)
    {
        supposeUserWithMobileExistsOnServer(mobile)
        supposeVerificationCodeIs(verificationCode)

        userEntersMobileAndClicksLogin(mobile)

        userWillBeProceededToCodeVerificationPage()

        userEntersVerificationCodeAndAsksToConfirmIt(verificationCode)

        userIsMovedToWelcomePage()
    }

    fun userIsSavedAsLoggedIn(mobile: String) {

        DatabaseGateway.instance.userDao().count() shouldBe 1
    }

    fun userOpensSideDrawerAndPressesLogout() {
        openDrawerAndNavigateToMenuItemAndClickIt(R.id.drawerLayout,R.id.navigation,R.id.loginMobilePage2)
    }

    fun userWillBeProceededToEnteringMobilePage() {
        ensureThatViewIsOnScreenNow(R.id.mobileField)
    }
}