package com.github.fsmaiorano.organic.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.fsmaiorano.organic.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getById(id: Long): Product?

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM Product ORDER BY name ASC")
    fun getAllOrderByNameAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY name DESC")
    fun getAllOrderByNameDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description ASC")
    fun getAllOrderByDescriptionAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description DESC")
    fun getAllOrderByDescriptionDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY price ASC")
    fun getAllOrderByPriceAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY price DESC")
    fun getAllOrderByPriceDesc(): List<Product>
}