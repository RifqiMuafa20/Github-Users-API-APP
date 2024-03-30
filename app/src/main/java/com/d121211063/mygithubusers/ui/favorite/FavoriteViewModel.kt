package com.d121211063.mygithubusers.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.d121211063.mygithubusers.data.local.entity.UserFavorite
import com.d121211063.mygithubusers.data.local.room.UserFavoriteDao
import com.d121211063.mygithubusers.data.local.room.UserFavoriteRoomDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userFavoritedDao: UserFavoriteDao? = null
    private var userFavoritedDb: UserFavoriteRoomDatabase? =
        UserFavoriteRoomDatabase.getDatabase(application)

    init {
        userFavoritedDao = userFavoritedDb?.userDao()
    }

    fun getFavoritedUsers(): LiveData<List<UserFavorite>>? {
        return userFavoritedDao?.getFavoritedUser()
    }
}