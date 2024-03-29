package com.d121211063.mygithubusers.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.data.local.entity.UserFavorite
import com.d121211063.mygithubusers.databinding.ItemUsersBinding
import com.d121211063.mygithubusers.ui.detail.DetailUserActivity

class FavoriteAdapter : ListAdapter<UserFavorite, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
            intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatar_url)
            intent.putExtra(DetailUserActivity.EXTRA_TYPE, user.type)
            it.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFavorite) {
            binding.tvItemName.text = user.login
            binding.userStatus.text = user.type
            Glide.with(binding.root)
                .load(user.avatar_url)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFavorite>() {
            override fun areItemsTheSame(oldItem: UserFavorite, newItem: UserFavorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserFavorite, newItem: UserFavorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}