package ir.avarche.android.app.loginPage

import retrofit2.Call
import javax.inject.Inject

interface LoginRepository {

    fun login(mobile:String):Call<User?>
    fun verifyCode(mobile: String,verificationCode: String): Call<Boolean>

}

class LoginRepo @Inject constructor(private val loginHttpGateway: LoginHttpGateway):LoginRepository
{
    override fun login(mobile: String) = loginHttpGateway.login(mobile)
    override fun verifyCode(mobile: String,verificationCode: String) = loginHttpGateway.verifyCode(mobile,verificationCode)
}