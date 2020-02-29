package ir.avarche.android.app.infrastructure.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val mobile:String)