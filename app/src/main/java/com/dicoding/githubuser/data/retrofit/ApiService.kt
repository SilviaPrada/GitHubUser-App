package com.dicoding.githubuser.data.retrofit

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getDataUser(@Query("q") q: String): Call<GitHubResponse>
    //suspend fun getDataUser(@Query("apiKey") apiKey: String): GitHubResponse

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}