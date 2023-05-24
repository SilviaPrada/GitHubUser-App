package com.dicoding.githubuser.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.local.User
import com.dicoding.githubuser.data.retrofit.ItemsItem
import com.dicoding.githubuser.databinding.ItemUserListBinding
import com.dicoding.githubuser.detail.DetailUserActivity

class DataUserAdapter(private val listDataUser: List<ItemsItem>) :
    RecyclerView.Adapter<DataUserAdapter.ListViewHolder>() {

    inner class ListViewHolder(var binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataUser = listDataUser[position]
        Glide.with(holder.itemView.context)
            .load(dataUser.avatarUrl)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = dataUser.login
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.USERNAME, dataUser.login)
            intentDetail.putExtra(DetailUserActivity.ID, dataUser.id)
            intentDetail.putExtra(DetailUserActivity.AVATAR, dataUser.avatarUrl)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listDataUser.size
}

