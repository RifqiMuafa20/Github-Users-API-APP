package com.d121211063.mygithubusers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.d121211063.mygithubusers.data.response.ItemsItem
import com.d121211063.mygithubusers.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val mainViewModel by viewModels<HomeViewModel>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(this.context, 2)
        binding.rvUsers.layoutManager = layoutManager

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        mainViewModel.listUser.observe(viewLifecycleOwner) { listUser ->
            setRUserData(listUser)
        }

        mainViewModel.isError.observe(viewLifecycleOwner) {
            showToastError(it)
        }

        searchUser()

        return root
    }

    private fun setRUserData(listUser: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
    }

    private fun searchUser() {
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isEmpty()) return true
                    mainViewModel.findUser(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToastError(isError: Boolean) {
        if (isError) Toast.makeText(this.context, "Terjadi kesalahan!! Mohon Bersabar", Toast.LENGTH_SHORT).show()
    }
}