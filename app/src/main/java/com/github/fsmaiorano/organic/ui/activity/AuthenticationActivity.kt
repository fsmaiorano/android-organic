package com.github.fsmaiorano.organic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.fsmaiorano.organic.databinding.ActivityAuthenticationBinding
import com.github.fsmaiorano.organic.databinding.ActivityFormUserSignupBinding
import com.github.fsmaiorano.organic.databinding.ActivityListProductBinding
import com.github.fsmaiorano.organic.extensions.goTo

class AuthenticationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSignUpButton()
        setSignInButton()
    }

    private fun setSignInButton() {
        binding.activityAuthenticationSignInButton.setOnClickListener {
            val user = binding.activityAuthenticationEditTextUser.text.toString()
            val password = binding.activityAuthenticationEditTextPassword.text.toString()
            Log.i("LoginActivity", "onCreate: $user - ")
            val intent = Intent(this, ListProductActivity::class.java).apply {

            }
            startActivity(intent)
        }
    }

    private fun setSignUpButton() {
        binding.activityAuthenticationSignUpButton.setOnClickListener {
            val intent = Intent(this, FormUserSignUpActivity::class.java).apply {

            }
            startActivity(intent)
        }
    }
}