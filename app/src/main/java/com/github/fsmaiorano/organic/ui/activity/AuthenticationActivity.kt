package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityAuthenticationBinding
import com.github.fsmaiorano.organic.extensions.goTo
import com.github.fsmaiorano.organic.preferences.dataStore
import kotlinx.coroutines.launch

class AuthenticationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater)
    }

    private val userDao by lazy {
        AppDatabase.instance(this).userDao()
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

            lifecycleScope.launch {
                userDao.authentication(user, password)?.let {
                    dataStore.edit { preferences ->
                        preferences[stringPreferencesKey("authenticatedUser")] = it.id.toString()
                    }
                    goTo(ListProductActivity::class.java) {
                        putExtra("userId", it.id)
                    }
                } ?: Toast.makeText(
                    this@AuthenticationActivity,
                    "User or password invalid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setSignUpButton() {
        binding.activityAuthenticationSignUpButton.setOnClickListener {
            goTo(FormUserSignUpActivity::class.java)
        }
    }
}