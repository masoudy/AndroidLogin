package ir.avarche.android.app.loginPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.avarche.android.app.util.EventStream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository:LoginRepository) : ViewModel() {

    var mobile:String = ""
    var verificationCode:String = ""

    val isLoggedIn:LiveData<Boolean> = MutableLiveData(false)
    val isInProgress:LiveData<Boolean> = MutableLiveData(false)

    var wrongCodeWarns: EventStream<Unit> = EventStream()
    var invalidMobileWarns: EventStream<Unit> = EventStream()
    var verificationCodeSent: EventStream<Boolean> = EventStream(false)


    fun login(){

        if(isMobileValid().not())
        {
            invalidMobileWarns.publish(Unit)
            return
        }

        if(isInProgress.value!!)
            return

        (isInProgress as MutableLiveData).value = true

        repository.login(mobile).enqueue(object:Callback<User?>{
            override fun onFailure(call: Call<User?>, t: Throwable) {
                t.printStackTrace()
                isInProgress.postValue(false)
                verificationCodeSent.publish(false)
            }

            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                isInProgress.postValue(false)
                verificationCodeSent.publish(response.body() != null)
            }
        })
    }

    private fun isMobileValid() =
        mobile.isNotBlank() && mobile.startsWith("09")  && mobile.trim().length == 11


    fun verifyCode() {

        if(isInProgress.value!!)
            return

        (isInProgress as MutableLiveData).value = true

        repository.verifyCode(mobile,verificationCode).enqueue(object:Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                t.printStackTrace()
                isInProgress.postValue(false)
                (isLoggedIn as MutableLiveData).value = false
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                isInProgress.postValue(false)

                if(response.body() == false)
                    wrongCodeWarns.publish(Unit)
                else
                    (isLoggedIn as MutableLiveData).value = true
            }
        })
    }
}