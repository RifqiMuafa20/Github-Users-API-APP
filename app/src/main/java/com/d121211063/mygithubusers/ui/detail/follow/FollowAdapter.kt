package com.d121211063.mygithubusers.ui.detail.follow

import android.content.Intent
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.MainActivity
import com.d121211063.mygithubusers.data.local.history.History
import com.d121211063.mygithubusers.data.remote.response.UserFollowersResponseItem
import com.d121211063.mygithubusers.databinding.ItemFollowUserBinding
import com.d121211063.mygithubusers.ui.detail.DetailUserActivity

class FollowAdapter : ListAdapter<UserFollowersResponseItem, FollowAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemFollowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            MainActivity.historyList.add(History(user.login, user.type, user.avatarUrl))
            val intent = Intent(it.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, user?.login)
            intent.putExtra(DetailUserActivity.EXTRA_AVATAR, user?.avatarUrl)
            intent.putExtra(DetailUserActivity.EXTRA_TYPE, user?.type)
            it.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemFollowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFollowersResponseItem) {
            binding.userName.text = user.login
            binding.userStatus.text = user.type
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserFollowersResponseItem,
                newItem: UserFollowersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowersResponseItem,
                newItem: UserFollowersResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}