package com.d121211063.mygithubusers.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.data.response.DetailUserResponse
import com.d121211063.mygithubusers.data.response.UserFollowersResponseItem
import com.d121211063.mygithubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: MutableLiveData<DetailUserResponse> = _detailUser

    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    init {
        getDetailUser(DetailUserActivity.username)
    }

    private fun getDetailUser(user : String?) {
        val client = ApiConfig.getApiService().getDetailUser(user.toString())
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ){
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}