package com.github.fsmaiorano.organic.dao

import com.github.fsmaiorano.organic.model.Product
import java.math.BigDecimal

class ProductDao {
    fun add(product: Product) {
        products.add(product)
    }

    fun getAll(): List<Product> {
        return products.toList()
    }

    companion object {
        private val products = mutableListOf<Product>(
            Product(
                "Banana",
                "A banana",
                BigDecimal(1.83)
            ),
            Product(
                "Apple",
                "An apple",
                BigDecimal(2.99)
            ),
            Product(
                "Orange",
                "An orange",
                BigDecimal(3.99)
            ),
        )
    }
}