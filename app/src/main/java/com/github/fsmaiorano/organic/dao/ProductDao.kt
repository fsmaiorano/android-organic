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
                "Salad",
                "A Salad",
                BigDecimal(1.83),
                imageUrl = "https://img.freepik.com/fotos-gratis/salada-de-tomate-pepino-cebola-roxa-e-folhas-de-alface-menu-de-vitamina-verao-saudavel-comida-vegetal-vegana-mesa-de-jantar-vegetariana-vista-do-topo-configuracao-plana_2829-6482.jpg?w=2000&t=st=1670080595~exp=1670081195~hmac=73cd5ac619cea294df58610c3a05ddd39729de4b2f847af3d36b40ebbdbabc48"
            ),
            Product(
                "Apple",
                "An apple",
                BigDecimal(2.99),
                imageUrl = "https://png.pngtree.com/element_our/png/20181129/vector-illustration-of-fresh-red-apple-with-single-leaf-png_248312.jpg"
            ),
            Product(
                "Orange",
                "An orange",
                BigDecimal(3.99)
            ),
        )
    }
}