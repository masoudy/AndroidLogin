package ir.avarche.android.test.doubles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ir.avarche.android.app.database.User
import ir.avarche.android.app.database.UserDao

class UserDaoMock:UserDao {

    private val liveData = MutableLiveData<List<User>>()

    private val liveData1 = Transformations.map(liveData) {
        it?.isNotEmpty() ?: false
    }!!

    override fun isThereAnyLoggedInUser() = liveData1

    override fun count() = liveData.value?.size ?: 0

    override fun all() = liveData

    override fun updateLoggedInUser(user: User) {
        liveData.postValue(listOf(user))
    }
}