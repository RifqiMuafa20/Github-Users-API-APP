package com.d121211063.mygithubusers.ui.home

import android.content.Intent
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.MainActivity
import com.d121211063.mygithubusers.data.local.entity.UserVisited
import com.d121211063.mygithubusers.data.remote.response.ItemsItem
import com.d121211063.mygithubusers.databinding.ItemUsersBinding
import com.d121211063.mygithubusers.ui.detail.DetailUserActivity

class UsersAdapter : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, user?.login)
            intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user?.avatarUrl)
            intent.putExtra(DetailUserActivity.EXTRA_TYPE, user?.type)
            it.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvItemName.text = user.login
            binding.userStatus.text = user.type
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}