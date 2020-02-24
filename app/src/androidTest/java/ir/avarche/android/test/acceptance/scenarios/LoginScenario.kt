package ir.avarche.android.test.acceptance.scenarios

import ir.avarche.android.app.CallWasIntercepted
import ir.avarche.android.app.ServerGateway
import ir.avarche.android.app.ServerInterceptor
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

    fun supposeUserWithMobileExists(mobile: String) {

        ServerGateway.addInterceptor(object : ServerInterceptor {
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

                    throw CallWasIntercepted(response)
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

    fun supposeVerificationCodeIs(code: String) {
        ServerGateway.addInterceptor(object : ServerInterceptor {
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

                    throw CallWasIntercepted(response)
                }
            }
        })
    }
}