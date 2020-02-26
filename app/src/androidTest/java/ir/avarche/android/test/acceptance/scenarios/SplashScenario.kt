package ir.avarche.android.test.acceptance.scenarios

import ir.avarche.android.app.CallWasIntercepted
import ir.avarche.android.app.ServerGateway
import ir.avarche.android.app.ServerInterceptor
import ir.avarche.android.test.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType

object SplashScenario {

    fun supposeThatThereIsNoLoggedInUserInCache()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun supposeUserWithMobileExistsOnLocalCacheAsLoggedInUser(mobile: String) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    fun ensureThatUserProceedsToLoginPage()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun ensureThatUserProceedsToMainPage()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun supposeUserOpensTheAppAndSeesSplashScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}