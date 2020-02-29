package ir.avarche.android.app.loginPage

import ir.avarche.android.app.database.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface LoginHttpGateway
{
    @GET("/login")
    suspend fun login(@Header("mobile") mobile:String):User?

    @GET("/verify")
    suspend fun verifyCode(@Header("mobile") mobile: String,@Header("code") verificationCode: String): Boolean
}