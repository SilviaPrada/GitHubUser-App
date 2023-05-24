package com.dicoding.githubuser.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.local.FavoriteUser
import com.dicoding.githubuser.data.local.FavoriteUserDao
import com.dicoding.githubuser.data.local.FavoriteUserDatabase
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.data.retrofit.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val listDetail = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var favoriteUserDao : FavoriteUserDao? = null
    private var favoriteUserDb : FavoriteUserDatabase? = null

    init {
        favoriteUserDb = FavoriteUserDatabase.getDatabase(application)
        favoriteUserDao = favoriteUserDb?.favoriteUserDao()
    }

    fun setDetailUser(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    listDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUser(): LiveData<DetailUserResponse>{
        return listDetail
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch{
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            favoriteUserDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = favoriteUserDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.removeFromFavorite(id)
        }
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}
