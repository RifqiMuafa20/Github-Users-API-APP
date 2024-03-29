package com.d121211063.mygithubusers.ui.profile

import androidx.lifecycle.LiveData
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.data.remote.response.DetailUserResponse
import com.d121211063.mygithubusers.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _myProfile = MutableLiveData<DetailUserResponse>()
    val myProfile: LiveData<DetailUserResponse> = _myProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    init {
        getMyProfile("RifqiMuafa20")
    }

    private fun getMyProfile(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username.toString())
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _myProfile.value = response.body()
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
}