package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.github.fsmaiorano.organic.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : BaseUserActivity() {

    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            getUserDetail()
        }
    }

    private suspend fun getUserDetail() {
        user.collect { user ->
            binding.activityProfileUser.setText(user?.name)
            binding.activityProfileLogoutButton.setOnClickListener {
                lifecycleScope.launch {
                    logoutUser()
                }
            }
        }
    }
}