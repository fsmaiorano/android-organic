package com.github.fsmaiorano.organic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.fsmaiorano.organic.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Insert
    fun insert(product: Product)
}