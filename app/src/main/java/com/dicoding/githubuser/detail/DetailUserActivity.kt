package com.dicoding.githubuser.detail

import com.dicoding.githubuser.adapter.SectionsPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.data.retrofit.DetailUserResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.title_bar_detail)

        val username = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(ID, 0)
        val avatar = intent.getStringExtra(AVATAR)

        detailViewModel.setDetailUser(username.toString())
        detailViewModel.getDetailUser().observe(this, {
            setDataUser(it)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0) {
                        binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(binding.fabAdd.context, R.drawable.ic_favorite))
                        isChecked = true
                    } else {
                        binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(binding.fabAdd.context, R.drawable.ic_favorite_border))
                        isChecked = false
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener{
            isChecked = !isChecked
            if (isChecked){
                detailViewModel.addToFavorite(username.toString(), id, avatar.toString())
                binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(binding.fabAdd.context, R.drawable.ic_favorite))
            }else{
                detailViewModel.removeFromFavorite(id)
                binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(binding.fabAdd.context, R.drawable.ic_favorite_border))
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setDataUser(item: DetailUserResponse) {
        binding.tvUsername.text = item.login
        binding.tvRealname.text = item.name
        binding.tvFollowers.text = resources.getString(R.string.followers, item.followers)
        binding.tvFollowing.text = resources.getString(R.string.following, item.following)
        Glide.with(this@DetailUserActivity)
            .load(item.avatarUrl)
            .into(binding.imgPhoto)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val USERNAME = "username"
        const val ID = "id"
        const val AVATAR = "avatar"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
