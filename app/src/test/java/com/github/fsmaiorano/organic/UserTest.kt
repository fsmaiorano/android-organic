package com.github.fsmaiorano.organic

import com.github.fsmaiorano.organic.model.User
import org.junit.Assert
import org.junit.Test

class UserTest {
    @Test
    fun onUserCreateIsValid() {
        val user = User(0, "test", "Test User", "123456")
        val isValid = user.isValid()
        Assert.assertTrue(isValid)
    }
}