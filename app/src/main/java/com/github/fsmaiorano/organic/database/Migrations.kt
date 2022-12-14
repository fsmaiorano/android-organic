package com.github.fsmaiorano.organic.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS 'User' (
                'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                'name' TEXT NOT NULL,
                'password' TEXT NOT NULL
            ) 
            """.trimIndent()
        )
    }
}

val MIGRATION2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            ALTER TABLE Product ADD COLUMN 'userId' TEXT
            """.trimIndent()
        )
    }
}