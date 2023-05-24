package com.dicoding.githubuser.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.adapter.DataUserAdapter
import com.dicoding.githubuser.data.local.FavoriteUser
import com.dicoding.githubuser.data.local.User
import com.dicoding.githubuser.data.retrofit.ItemsItem
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.title_bar_favorite)

        binding.rvDataFavorite.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        binding.rvDataFavorite.layoutManager = layoutManager

        favoriteViewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                val list = mapList(it)
                val adapter = DataUserAdapter(list)
                binding.rvDataFavorite.adapter = adapter
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): List<ItemsItem> {
        val listUser = ArrayList<ItemsItem>()
        for (user in users){
            val userMapped = ItemsItem(
                user.login,
                user.id,
                user.avatarUrl
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}