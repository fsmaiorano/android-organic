package com.github.fsmaiorano.organic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.github.fsmaiorano.organic.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun save(user: User)

}