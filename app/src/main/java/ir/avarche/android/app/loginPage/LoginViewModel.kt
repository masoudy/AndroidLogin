package ir.avarche.android.app.loginPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.avarche.android.app.infrastructure.repos.LoginRepository
import ir.avarche.android.app.infrastructure.util.EventStream
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    var mobile:String = ""
    var verificationCode:String = ""

    val isLoggedIn:LiveData<Boolean> = MutableLiveData(false)
    val isInProgress:LiveData<Boolean> = MutableLiveData(false)

    var wrongCodeWarns: EventStream<Unit> = EventStream()
    var invalidMobileWarns: EventStream<Unit> = EventStream()
    var verificationCodeSent: EventStream<Boolean> = EventStream(false)

    val isLoginButtonEnabled:Boolean
        get() = mobile.length > 11

    fun login(){

        if(isMobileValid().not())
        {
            invalidMobileWarns.publish(Unit)
            return
        }

        if(isInProgress.value!!)
            return

        viewModelScope.launch{
            loginProcess()
        }
    }

    private suspend fun loginProcess() {
        (isInProgress as MutableLiveData).value = true

        val user = try {
            repository.login(mobile)
        } catch (e: Throwable) {
            null
        }


        isInProgress.postValue(false)
        verificationCodeSent.publish(user != null)
    }

    private fun isMobileValid() =
        mobile.isNotBlank() && mobile.startsWith("09")  && mobile.trim().length == 11


    fun verifyCode() {

        if(isInProgress.value!!)
            return

        viewModelScope.launch {
            verifyProcess()
        }
    }

    private suspend fun verifyProcess() {
        (isInProgress as MutableLiveData).value = true

        (isLoggedIn as MutableLiveData).value = try {
            val result = repository.verifyCode(mobile, verificationCode)

            if (!result)
                wrongCodeWarns.publish(Unit)

            result
        } catch (e: Throwable) {
            false
        }

        isInProgress.postValue(false)
    }
}