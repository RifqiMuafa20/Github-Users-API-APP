package com.d121211063.mygithubusers.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.d121211063.mygithubusers.MainActivity
import com.d121211063.mygithubusers.R
import com.d121211063.mygithubusers.databinding.ActivitySplashScreenBinding
import com.d121211063.mygithubusers.ui.dark_mode.SettingPreferences
import com.d121211063.mygithubusers.ui.dark_mode.SettingViewModel
import com.d121211063.mygithubusers.ui.dark_mode.ViewModelFactory
import com.d121211063.mygithubusers.ui.dark_mode.dataStore

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val delay : Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(this.application.dataStore)
        val themeViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        supportActionBar?.hide()

        binding.progressBar.visibility = View.VISIBLE

        binding.progressBar.isIndeterminate = true
        startMainActivityWithDelay()
    }

    private fun startMainActivityWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delay)
    }
}