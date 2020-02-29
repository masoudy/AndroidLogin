package ir.avarche.android.app.infrastructure.httpGateways

import ir.avarche.android.app.infrastructure.database.User
import retrofit2.http.GET
import retrofit2.http.Header

interface LoginHttpGateway
{
    @GET("/login")
    suspend fun login(@Header("mobile") mobile:String):User?

    @GET("/verify")
    suspend fun verifyCode(@Header("mobile") mobile: String,@Header("code") verificationCode: String): Boolean
}