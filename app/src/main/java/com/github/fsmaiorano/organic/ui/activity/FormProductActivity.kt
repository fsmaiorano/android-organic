package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.github.fsmaiorano.organic.dao.ProductDao
import com.github.fsmaiorano.organic.databinding.ActivityFormProductBinding
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.ui.dialog.FormImageDialog
import java.math.BigDecimal


class FormProductActivity : AppCompatActivity() {
    private val dao = ProductDao()
    private var url: String? = null
    private val binding by lazy { ActivityFormProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "New Product"
        setSaveButton()

        binding.activityFormProductImage.setOnClickListener {
            FormImageDialog(this).show(url) { imageUrl ->
                url = imageUrl
                binding.activityFormProductImage.load(url)
            }
        }
    }

    private fun setSaveButton() {
        val btnSave = binding.activityFormProductButtonSave
        btnSave.setOnClickListener {
            val newProduct = createProduct()
            dao.add(newProduct)
            finish()
        }
    }

    private fun createProduct(): Product {
        val name = binding.activityFormProductEdittextName.text.toString()

        val description = binding.activityFormProductEdittextDescription.text.toString()

        val priceInText = binding.activityFormProductEdittextPrice.text.toString()
        val price = if (priceInText.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(priceInText)
        }
        return Product(name = name, description = description, price = price, imageUrl = url)
    }
}