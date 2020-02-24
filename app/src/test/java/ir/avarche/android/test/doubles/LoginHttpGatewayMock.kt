package ir.avarche.android.test.doubles

import ir.avarche.android.app.loginPage.LoginHttpGateway
import ir.avarche.android.app.loginPage.User
import retrofit2.Call

class LoginHttpGatewayMock:LoginHttpGateway {
    override fun login(mobile: String): Call<User?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}