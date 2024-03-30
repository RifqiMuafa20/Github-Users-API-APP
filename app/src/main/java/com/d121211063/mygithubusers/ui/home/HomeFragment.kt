package com.d121211063.mygithubusers.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.d121211063.mygithubusers.R
import com.d121211063.mygithubusers.data.remote.response.ItemsItem
import com.d121211063.mygithubusers.databinding.FragmentHomeBinding
import com.d121211063.mygithubusers.ui.dark_mode.DarkModeActivity
import com.d121211063.mygithubusers.ui.dark_mode.SettingPreferences
import com.d121211063.mygithubusers.ui.dark_mode.SettingViewModel
import com.d121211063.mygithubusers.ui.dark_mode.ViewModelFactory
import com.d121211063.mygithubusers.ui.dark_mode.dataStore

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

        setHasOptionsMenu(true)

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

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        val themeViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        themeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        searchUser()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.setting_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_setting -> {
                val intent = Intent(this.context, DarkModeActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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
        if (isError) Toast.makeText(
            this.context,
            "Terjadi kesalahan!! Mohon Bersabar",
            Toast.LENGTH_SHORT
        ).show()
    }
}