package ir.avarche.android.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val mobile:String)