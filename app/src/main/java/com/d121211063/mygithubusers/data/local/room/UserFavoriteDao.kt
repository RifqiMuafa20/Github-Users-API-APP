package com.d121211063.mygithubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.d121211063.mygithubusers.data.local.entity.UserFavorite

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(user: UserFavorite)

    @Query("SELECT * FROM user_favorited")
    fun getFavoritedUser(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorited WHERE user_favorited.login = :login")
    fun checkUser(login: String): Int

    @Query("DELETE FROM user_favorited WHERE user_favorited.login = :login")
    fun removeFavorite(login: String)
}