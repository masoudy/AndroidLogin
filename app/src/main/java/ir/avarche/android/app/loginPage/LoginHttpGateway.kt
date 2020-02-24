package ir.avarche.android.app.loginPage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface LoginHttpGateway
{
    @GET("/login")
    fun login(@Header("mobile") mobile:String):Call<User?>

    @GET("/verify")
    fun verifyCode(@Header("mobile") mobile: String,@Header("code") verificationCode: String): Call<Boolean>
}