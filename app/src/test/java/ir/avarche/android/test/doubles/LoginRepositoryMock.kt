package ir.avarche.android.test.doubles

import androidx.lifecycle.LiveData
import io.kotlintest.shouldBe
import ir.avarche.android.app.loginPage.LoginRepository
import ir.avarche.android.app.database.User
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryMock() : LoginRepository {

    private var wasCalledWithMobile = ""
    private var wasCalledWithCode = ""
    private var wasCalledWithVerificationMobile = ""

    var nextCallToLoginReturnBody: User? = null
    var nextCallToLoginVerifyReturnBody = false

    override val isThereAnyLoggedInUser: LiveData<Boolean>
        get() = throw Exception()

    override suspend fun login(mobile: String): User? {
        wasCalledWithMobile = mobile
        return nextCallToLoginReturnBody
    }

    override suspend fun verifyCode(mobile: String, verificationCode: String): Boolean {
        wasCalledWithCode = verificationCode
        wasCalledWithVerificationMobile = mobile
        return nextCallToLoginVerifyReturnBody
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