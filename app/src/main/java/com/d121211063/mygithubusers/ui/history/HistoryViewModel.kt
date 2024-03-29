package com.d121211063.mygithubusers.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.MainActivity
import com.d121211063.mygithubusers.data.local.entity.UserVisited
import com.d121211063.mygithubusers.data.local.room.UserVisitedDao
import com.d121211063.mygithubusers.data.local.room.UserVisitedRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private var userVisitedDao: UserVisitedDao? = null
    private var userVisitedDb: UserVisitedRoomDatabase? = UserVisitedRoomDatabase.getDatabase(application)

    init {
        userVisitedDao = userVisitedDb?.userDao()
    }

    fun getVisitedUsers(): LiveData<List<UserVisited>>? {
        return userVisitedDao?.getVisitedUser()
    }

    fun deleteAllVisited() {
        CoroutineScope(Dispatchers.IO).launch {
            userVisitedDao?.deleteAll()
        }
    }
}