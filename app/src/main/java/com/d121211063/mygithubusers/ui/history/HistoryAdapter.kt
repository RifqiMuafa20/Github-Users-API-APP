package com.d121211063.mygithubusers.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.data.local.entity.UserVisited
import com.d121211063.mygithubusers.databinding.ItemUsersBinding
import com.d121211063.mygithubusers.ui.detail.DetailUserActivity

class HistoryAdapter : ListAdapter<UserVisited, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, history.login)
            intent.putExtra(DetailUserActivity.EXTRA_AVATAR, history.avatar_url)
            intent.putExtra(DetailUserActivity.EXTRA_TYPE, history.type)
            it.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userVisited: UserVisited) {
            binding.tvItemName.text = userVisited.login
            binding.userStatus.text = userVisited.type
            Glide.with(binding.root)
                .load(userVisited.avatar_url)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserVisited>() {
            override fun areItemsTheSame(oldItem: UserVisited, newItem: UserVisited): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserVisited, newItem: UserVisited): Boolean {
                return oldItem == newItem
            }
        }
    }
}