package com.d121211063.mygithubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.d121211063.mygithubusers.data.local.entity.UserVisited

@Dao
interface UserVisitedDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVisited(user: UserVisited)

    @Query("SELECT * FROM user_visited")
    fun getVisitedUser(): LiveData<List<UserVisited>>

    @Query("DELETE FROM user_visited")
    fun deleteAll()
}