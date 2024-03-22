package com.d121211063.mygithubusers.ui.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.data.response.UserFollowersResponse
import com.d121211063.mygithubusers.data.response.UserFollowersResponseItem
import com.d121211063.mygithubusers.data.retrofit.ApiConfig
import com.d121211063.mygithubusers.ui.detail.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _listFollowing = MutableLiveData<List<UserFollowersResponseItem>>()
    val listFollowing: LiveData<List<UserFollowersResponseItem>> = _listFollowing

    private val _listFollowers = MutableLiveData<List<UserFollowersResponseItem>>()
    val listFollowers: LiveData<List<UserFollowersResponseItem>> = _listFollowers

    companion object{
        private const val TAG = "FollowViewModel"
    }

    init {
        findFollowers(DetailUserActivity.username)
        findFollowing(DetailUserActivity.username)
    }

    private fun findFollowers(user: String) {
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<List<UserFollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowersResponseItem>>,
                response: Response<List<UserFollowersResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun findFollowing(user : String) {
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<UserFollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowersResponseItem>>,
                response: Response<List<UserFollowersResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}