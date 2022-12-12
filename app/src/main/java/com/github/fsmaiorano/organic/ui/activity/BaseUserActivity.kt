package com.github.fsmaiorano.organic.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.extensions.goTo
import com.github.fsmaiorano.organic.model.User
import com.github.fsmaiorano.organic.preferences.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class BaseUserActivity : AppCompatActivity() {
    private val userDao by lazy {
        AppDatabase.instance(this).userDao()
    }

    private var _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected var user: StateFlow<User?> = _user


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verifyAuthenticatedUser()
        }
    }

    private suspend fun verifyAuthenticatedUser() {
        dataStore.data.collect { preferences ->
            preferences[stringPreferencesKey("authenticatedUser")]?.let { userId ->
                searchUser(userId)
            } ?: goToAuthentication()
        }
    }

    private fun searchUser(userId: String) {
        lifecycleScope.launch {
            _user.value = userDao.getById(userId.toLong())?.firstOrNull()
        }
    }

    protected suspend fun logoutUser() {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey("authenticatedUser"))
        }
        goToAuthentication()
    }

    private fun goToAuthentication() {
        goTo(AuthenticationActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
}
