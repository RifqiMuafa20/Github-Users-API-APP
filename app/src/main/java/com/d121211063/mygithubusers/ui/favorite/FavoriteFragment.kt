package com.d121211063.mygithubusers.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.d121211063.mygithubusers.data.local.entity.UserFavorite
import com.d121211063.mygithubusers.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var _binding: FragmentFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(this.context, 2)
        binding.rvUsers.layoutManager = layoutManager

        favoriteViewModel.getFavoritedUsers()?.observe(viewLifecycleOwner) { listUser ->
            setFavoritedData(listUser)
        }

        return root
    }

    private fun setFavoritedData(listUser: List<UserFavorite>) {
        val adapter = FavoriteAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
    }
}