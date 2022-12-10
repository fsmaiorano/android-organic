package com.github.fsmaiorano.organic.database.dao

import androidx.room.*
import com.github.fsmaiorano.organic.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getById(id: Long): Flow<Product>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM Product ORDER BY name ASC")
    suspend fun getAllOrderByNameAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY name DESC")
    suspend fun getAllOrderByNameDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description ASC")
    suspend fun getAllOrderByDescriptionAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description DESC")
    suspend fun getAllOrderByDescriptionDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY price ASC")
    suspend fun getAllOrderByPriceAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY price DESC")
    suspend fun getAllOrderByPriceDesc(): List<Product>
}