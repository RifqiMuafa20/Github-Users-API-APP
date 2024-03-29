package com.d121211063.mygithubusers.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.d121211063.mygithubusers.data.local.entity.UserVisited

@Database(entities = [UserVisited::class], version = 1, exportSchema = false)
abstract class UserVisitedRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserVisitedDao

    companion object {
        @Volatile
        private var INSTANCE: UserVisitedRoomDatabase? = null

        fun getDatabase(context: Context): UserVisitedRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserVisitedRoomDatabase::class.java, "UsersVisited.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}