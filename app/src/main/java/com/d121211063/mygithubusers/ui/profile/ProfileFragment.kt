package com.d121211063.mygithubusers.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.d121211063.mygithubusers.R
import com.d121211063.mygithubusers.data.remote.response.DetailUserResponse
import com.d121211063.mygithubusers.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        profileViewModel.myProfile.observe(viewLifecycleOwner) { profile ->
            getUserDetail(profile)
        }

        profileViewModel.isError.observe(viewLifecycleOwner) {
            showToastError(it)
        }

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getUserDetail(profile: DetailUserResponse) {
        with(binding) {
            Glide.with(this@ProfileFragment)
                .load(profile.avatarUrl)
                .circleCrop()
                .into(imageView)
            namaLengkap.text = profile.name ?: "Not Available"
            userName.text = profile.login
            userBio.text = (profile.bio ?: "Not Available").toString()
            companyUser.text = profile.company ?: "Not Available"
            LocationUser.text = profile.location ?: "Not Available"

            val nfollower = profile.followers
            val nfollowing = profile.following
            val nrepo = profile.publicRepos

            follower.text = getString(R.string.follower, nfollower)
            following.text = getString(R.string.following, nfollowing)
            repositori.text = getString(R.string.repositories, nrepo)
        }
    }

    private fun showToastError(isError: Boolean) {
        if (isError) Toast.makeText(this.context, "Terjadi kesalahan!! Mohon Bersabar", Toast.LENGTH_SHORT).show()
    }
}