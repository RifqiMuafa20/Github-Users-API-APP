package com.d121211063.mygithubusers.ui.detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.d121211063.mygithubusers.data.response.UserFollowersResponseItem
import com.d121211063.mygithubusers.databinding.FragmentFollowsBinding

class FollowFragment : Fragment() {

    private lateinit var _binding: FragmentFollowsBinding
    private val followViewModel by viewModels<FollowViewModel>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(this.context)
        binding.rvFollow.layoutManager = layoutManager

        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (position == 1) {
            followViewModel.listFollowers.observe(viewLifecycleOwner) { listFollow ->
                setFollowData(listFollow)
            }
        } else {
            followViewModel.listFollowing.observe(viewLifecycleOwner) { listFollow ->
                setFollowData(listFollow)
            }
        }

        followViewModel.isError.observe(viewLifecycleOwner) {
            showToastError(it)
        }

        return root
    }

    private fun setFollowData(listFollow: List<UserFollowersResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(listFollow)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToastError(isError: Boolean) {
        if (isError) Toast.makeText(this.context, "Terjadi kesalahan!! Mohon Bersabar", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ARG_POSITION = "position"

        var position = 0
    }
}