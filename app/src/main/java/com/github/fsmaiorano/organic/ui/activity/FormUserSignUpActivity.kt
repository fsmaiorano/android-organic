package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityFormUserSignupBinding
import com.github.fsmaiorano.organic.extensions.toast
import com.github.fsmaiorano.organic.model.User
import kotlinx.coroutines.launch

class FormUserSignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFormUserSignupBinding.inflate(layoutInflater) }

    private val userDao by lazy { AppDatabase.instance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSignUpButton()
    }

    private fun setSignUpButton() {
        binding.activityFormUserSignupButtonSignup.setOnClickListener {
            val newUser = createUser()
            Log.i("FormUserSignUpActivity", "onCreate: $newUser")
            doSignUp(newUser)
        }
    }

    private fun doSignUp(newUser: User) {
        lifecycleScope.launch {
            try {
                userDao.save(newUser)
                finish()
            } catch (e: Exception) {
                Log.e("FormUserSignUpActivity", "onCreate: ", e)
                toast("Sign up failed")
            }
        }
    }

    private fun createUser(): User {
        val user = binding.activityFormUserSignupEditTextUser.text.toString()
        val name = binding.activityFormUserSignupEditTextName.text.toString()
        val password = binding.activityFormUserSignupEditTextPassword.text.toString()
        return User(0, user, name, password)
    }
}