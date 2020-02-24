package ir.avarche.android.test.doubles

import io.kotlintest.shouldBe
import ir.avarche.android.app.loginPage.LoginRepository
import ir.avarche.android.app.loginPage.User
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryMock:LoginRepository {

    private var wasCalledWithMobile = ""
    private var wasCalledWithCode = ""
    private var wasCalledWithVerificationMobile = ""

    var nextCallToLoginReturnBody:User? = null
    var nextCallToLoginVerifyReturnBody = false

    override fun login(mobile: String): Call<User?> {
        wasCalledWithMobile = mobile
        return CallMock(nextCallToLoginReturnBody)
    }

    override fun verifyCode(mobile: String,verificationCode: String): Call<Boolean> {
        wasCalledWithCode = verificationCode
        wasCalledWithVerificationMobile = mobile
        return CallMock(nextCallToLoginVerifyReturnBody)
    }

    fun ensureThatWasAskedToLoginWithMobile(mobile: String) {
        wasCalledWithMobile shouldBe  mobile
    }

    fun ensureThatWasAskedToVerifyCode(mobile:String,code: String) {
        wasCalledWithVerificationMobile shouldBe  mobile
        wasCalledWithCode shouldBe  code

    }
}

class CallMock<T>(val returnedBody:T):Call<T>
{
    override fun enqueue(callback: Callback<T>) {

        callback.onResponse(this, Response.success(returnedBody))

    }

    override fun isExecuted(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clone(): Call<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCanceled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun execute(): Response<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun request(): Request {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}