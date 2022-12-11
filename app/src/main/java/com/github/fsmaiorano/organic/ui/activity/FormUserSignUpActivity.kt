package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.fsmaiorano.organic.databinding.ActivityFormUserSignupBinding
import com.github.fsmaiorano.organic.model.User

class FormUserSignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFormUserSignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSignUpButton()
    }

    private fun setSignUpButton() {
        binding.activityFormUserSignupButtonSignup.setOnClickListener {
            val newUser = createUser()
            Log.i("SignUp", "onCreate: $newUser")
            finish()
        }
    }

    private fun createUser(): User {
        val user = binding.activityFormUserSignupEditTextUser.text.toString()
        val name = binding.activityFormUserSignupEditTextName.text.toString()
        val password = binding.activityFormUserSignupEditTextPassword.text.toString()
        return User(user, name, password)
    }
}