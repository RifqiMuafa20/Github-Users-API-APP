package com.d121211063.mygithubusers.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.data.response.ItemsItem
import com.d121211063.mygithubusers.data.response.UserResponse
import com.d121211063.mygithubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    companion object{
        var username: String? = null
        private const val TAG = "MainViewModel"
    }

    init {
        if (username != null) {
            findUser(username!!)
        } else {
            findUser("rifqi")
        }
    }

    private fun findUser(user : String) {
        val client = ApiConfig.getApiService().getUser(user)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}