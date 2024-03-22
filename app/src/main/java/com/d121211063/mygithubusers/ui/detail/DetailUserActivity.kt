package com.d121211063.mygithubusers.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.R
import com.d121211063.mygithubusers.data.response.DetailUserResponse
import com.d121211063.mygithubusers.data.response.ItemsItem
import com.d121211063.mygithubusers.databinding.ActivityDetailUserBinding
import com.d121211063.mygithubusers.ui.home.UsersAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        var EXTRA_USER = "extra_user"
        var username: String = ""
    }

    private lateinit var _binding: ActivityDetailUserBinding
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USER).toString()

        mainViewModel.detailUser.observe(this) { detailUser ->
            getUserDetail(detailUser)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
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
}