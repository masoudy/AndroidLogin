package ir.avarche.android.test.doubles

import ir.avarche.android.app.loginPage.LoginHttpGateway
import ir.avarche.android.app.database.User

class LoginHttpGatewayMock:LoginHttpGateway {

    var nextCallToVerifyLogin = false

    override suspend fun login(mobile: String): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun verifyCode(mobile: String, verificationCode: String): Boolean {
        return nextCallToVerifyLogin
    }
}