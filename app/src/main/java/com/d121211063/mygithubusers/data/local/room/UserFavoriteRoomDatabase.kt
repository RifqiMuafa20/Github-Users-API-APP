package com.d121211063.mygithubusers.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.d121211063.mygithubusers.data.local.entity.UserFavorite

@Database(entities = [UserFavorite::class], version = 1, exportSchema = false)
abstract class UserFavoriteRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserFavoriteRoomDatabase? = null

        fun getDatabase(context: Context): UserFavoriteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserFavoriteRoomDatabase::class.java, "UsersFavorite.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}