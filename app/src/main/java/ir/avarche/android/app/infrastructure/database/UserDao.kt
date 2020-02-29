package ir.avarche.android.app.infrastructure.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao
{
    @Query("select count(*)>0 from User")
    fun isThereAnyLoggedInUser(): LiveData<Boolean>

    @Query("select count(*) from User")
    fun count(): Int

    @Query("select * from User")
    fun all(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLoggedInUser(user:User)
}