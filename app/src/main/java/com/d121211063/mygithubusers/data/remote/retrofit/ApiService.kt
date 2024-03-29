package com.d121211063.mygithubusers.data.remote.retrofit

import com.d121211063.mygithubusers.data.remote.response.DetailUserResponse
import com.d121211063.mygithubusers.data.remote.response.UserFollowersResponseItem
import com.d121211063.mygithubusers.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") user: String
    ): Call<UserResponse>

    @GET("users/{user}")
    fun getDetailUser(
        @Path("user") user: String
    ): Call<DetailUserResponse>

    @GET("users/{user}/followers")
    fun getFollowers(
        @Path("user") user: String
    ): Call<List<UserFollowersResponseItem>>

    @GET("users/{user}/following")
    fun getFollowing(
        @Path("user") user: String
    ): Call<List<UserFollowersResponseItem>>

}