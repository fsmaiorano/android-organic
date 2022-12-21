package com.github.fsmaiorano.organic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.fsmaiorano.organic.database.converter.Converters
import com.github.fsmaiorano.organic.database.dao.ProductDao
import com.github.fsmaiorano.organic.database.dao.UserDao
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.model.User

@Database(entities = [Product::class, User::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instance(context: Context): AppDatabase {
            //Singleton
            return db ?: Room.databaseBuilder(context, AppDatabase::class.java, "organic.db")
                //.fallbackToDestructiveMigration()
                .addMigrations(MIGRATION1_2, MIGRATION2_3).build().also {
                    db = it
                }
        }
    }
}
