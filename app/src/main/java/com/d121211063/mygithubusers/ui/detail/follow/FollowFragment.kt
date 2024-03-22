package com.d121211063.mygithubusers.ui.detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        if (position == 1){
            followViewModel.listFollowers.observe(viewLifecycleOwner) { listFollow ->
                setFollowData(listFollow)
            }
        } else {
            followViewModel.listFollowing.observe(viewLifecycleOwner) { listFollow ->
                setFollowData(listFollow)
            }
        }

        return root
    }

    private fun setFollowData(listFollow: List<UserFollowersResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(listFollow)
        binding.rvFollow.adapter = adapter
    }

    companion object {
        const val ARG_POSITION = "position"

        var position = 0
    }
}