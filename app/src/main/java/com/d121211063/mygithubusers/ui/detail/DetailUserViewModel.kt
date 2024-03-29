package com.d121211063.mygithubusers.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.d121211063.mygithubusers.data.local.entity.UserFavorite
import com.d121211063.mygithubusers.data.local.entity.UserVisited
import com.d121211063.mygithubusers.data.local.room.UserFavoriteDao
import com.d121211063.mygithubusers.data.local.room.UserFavoriteRoomDatabase
import com.d121211063.mygithubusers.data.local.room.UserVisitedDao
import com.d121211063.mygithubusers.data.local.room.UserVisitedRoomDatabase
import com.d121211063.mygithubusers.data.remote.response.DetailUserResponse
import com.d121211063.mygithubusers.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private var userFavoriteDao: UserFavoriteDao?
    private var userFavoriteDb: UserFavoriteRoomDatabase? = UserFavoriteRoomDatabase.getDatabase(application)

    private var userVisitedDao: UserVisitedDao?
    private var userVisitedDb: UserVisitedRoomDatabase? = UserVisitedRoomDatabase.getDatabase(application)

    init {
        userFavoriteDao = userFavoriteDb?.userDao()
        userVisitedDao = userVisitedDb?.userDao()
        getDetailUser(DetailUserActivity.username)
    }

    private fun getDetailUser(user: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user.toString())
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addFavorite(username: String, avatarUrl: String, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavorite(
                username, avatarUrl, type
            )
            userFavoriteDao?.addFavorite(user)
        }
    }

    fun checkUser(login: String) = userFavoriteDao?.checkUser(login)

    fun removeFavorite(login: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userFavoriteDao?.removeFavorite(login)
        }
    }

    fun addVisited(username: String, avatarUrl: String, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserVisited(
                username, avatarUrl, type
            )
            userVisitedDao?.addVisited(user)
        }
    }

    companion object {
        private const val TAG = "DetailUserViewModel"
    }
}