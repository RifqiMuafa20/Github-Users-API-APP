package com.d121211063.mygithubusers.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.R
import com.d121211063.mygithubusers.data.remote.response.DetailUserResponse
import com.d121211063.mygithubusers.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    companion object {
        var EXTRA_AVATAR = "extra_avatar"
        var EXTRA_TYPE = "extra_type"
        var EXTRA_USER = "extra_user"

        var username: String = ""
        var url: String = ""
        var type: String = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var _binding: ActivityDetailUserBinding
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USER).toString()
        url = intent.getStringExtra(EXTRA_AVATAR).toString()
        type = intent.getStringExtra(EXTRA_TYPE).toString()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.detailUser.observe(this) { detailUser ->
            getUserDetail(detailUser)
        }

        mainViewModel.isError.observe(this) {
            showToastError(it)
        }

        mainViewModel.addVisited(username, url, type)

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = mainViewModel.checkUser(username)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24)
                        checked = true
                    } else {
                        binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                        checked = false
                    }
                }
            }
        }

        binding.ivFavorite.setOnClickListener {
            checked = !checked
            if (checked) {
                mainViewModel.addFavorite(username, url, type)
                binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                mainViewModel.removeFavorite(username)
                binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun getUserDetail(detailUser: DetailUserResponse) {
        with(binding) {
            Glide.with(this@DetailUserActivity)
                .load(detailUser.avatarUrl)
                .circleCrop()
                .into(imageView)
            namaLengkap.text = detailUser.name ?: "Not Available"
            userName.text = detailUser.login
            companyUser.text = detailUser.company ?: "Not Available"
            LocationUser.text = detailUser.location ?: "Not Available"

            val nfollower = detailUser.followers
            val nfollowing = detailUser.following

            follower.text = getString(R.string.follower, nfollower)
            following.text = getString(R.string.following, nfollowing)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToastError(isError: Boolean) {
        if (isError) Toast.makeText(this, "Terjadi kesalahan!! Mohon Bersabar", Toast.LENGTH_SHORT)
            .show()
    }
}