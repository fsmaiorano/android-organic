package com.github.fsmaiorano.organic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.fsmaiorano.organic.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun save(user: User)

    @Query(
        """
            SELECT * FROM User 
            WHERE user = :user 
            AND password = :password
        """
    )
    suspend fun authentication(user: String, password: String): User?

    @Query(
        """
            SELECT * FROM User 
            WHERE id = :userId
        """
    )
    fun getById(userId: String): Flow<User>?
}