package com.github.fsmaiorano.organic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.fsmaiorano.organic.database.dao.ProductDao
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.database.converter.Converters

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
