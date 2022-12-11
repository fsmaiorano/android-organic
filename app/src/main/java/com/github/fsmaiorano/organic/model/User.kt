package com.github.fsmaiorano.organic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val user: String,
    val name: String,
    val password: String
)
