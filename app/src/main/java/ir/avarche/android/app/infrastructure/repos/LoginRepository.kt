package ir.avarche.android.app.infrastructure.repos

import androidx.lifecycle.LiveData
import ir.avarche.android.app.infrastructure.database.User
import ir.avarche.android.app.infrastructure.database.UserDao
import ir.avarche.android.app.infrastructure.httpGateways.LoginHttpGateway
import javax.inject.Inject

interface LoginRepository {

    val isThereAnyLoggedInUser: LiveData<Boolean>

    suspend fun login(mobile:String):User?
    suspend fun verifyCode(mobile: String,verificationCode: String): Boolean

}

class LoginRepo @Inject constructor(private val loginHttpGateway: LoginHttpGateway, private val userDao: UserDao):
    LoginRepository
{
    override suspend fun login(mobile: String) = loginHttpGateway.login(mobile)
    override suspend fun verifyCode(mobile: String,verificationCode: String):Boolean {
        val result = loginHttpGateway.verifyCode(mobile,verificationCode)

        if(result)
            userDao.updateLoggedInUser(User(mobile))

        return result
    }

    override val isThereAnyLoggedInUser: LiveData<Boolean>
        get() = userDao.isThereAnyLoggedInUser()
}