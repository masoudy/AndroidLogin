package ir.avarche.android.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.atomic.AtomicReference

@Database(entities = [User::class],version = 1)
abstract class DatabaseGateway  : RoomDatabase()
{
    companion object
    {
        private  var theInstance:DatabaseGateway? = null


        fun initialize(context:Context,inMemory:Boolean = false): DatabaseGateway {
            theInstance?.close()

            theInstance=
                if(inMemory)
                    Room.inMemoryDatabaseBuilder(context,DatabaseGateway::class.java).build()
                else
                    Room.databaseBuilder(context,DatabaseGateway::class.java,"database").build()

            return theInstance!!
        }

        fun cleanUp()
        {
            theInstance?.close()
        }

        val instance:DatabaseGateway
            get() = theInstance!!



    }


    abstract fun userDao():UserDao
}
