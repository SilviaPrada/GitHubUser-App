package com.dicoding.githubuser.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.FavoriteUser
import com.dicoding.githubuser.data.local.FavoriteUserDao
import com.dicoding.githubuser.data.local.FavoriteUserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var favoriteUserDao : FavoriteUserDao? = null
    private var favoriteUserDb : FavoriteUserDatabase? = null

    init {
        favoriteUserDb = FavoriteUserDatabase.getDatabase(application)
        favoriteUserDao = favoriteUserDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return favoriteUserDao?.getFavoriteUser()
    }
}