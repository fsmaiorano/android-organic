package com.github.fsmaiorano.organic

import com.github.fsmaiorano.organic.model.Product
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class ProductTest {
    @Test
    fun onProductCreateHasValidPrice() {
        val product = Product(0, "Test Product", "https://www.google.com", BigDecimal(10.0), "")

        val isValidPrice = product.priceIsValid;
        Assert.assertTrue(isValidPrice)
    }
}
