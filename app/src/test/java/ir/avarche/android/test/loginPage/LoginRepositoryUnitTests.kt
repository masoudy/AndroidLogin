package ir.avarche.android.test.loginPage

import ir.avarche.android.app.loginPage.LoginRepo
import ir.avarche.android.test.doubles.LoginHttpGatewayMock

class LoginRepositoryUnitTests {

    private val repo = LoginRepo(LoginHttpGatewayMock())



}