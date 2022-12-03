package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.github.fsmaiorano.organic.R
import com.github.fsmaiorano.organic.dao.ProductDao
import com.github.fsmaiorano.organic.databinding.ActivityFormProductBinding
import com.github.fsmaiorano.organic.databinding.ProductImageFormBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage
import com.github.fsmaiorano.organic.model.Product
import java.math.BigDecimal


class FormProductActivity : AppCompatActivity() {
    private val dao = ProductDao()
    private var url: String? = null
    private val binding by lazy { ActivityFormProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSaveButton()

        binding.activityFormProductImage.setOnClickListener {
            val bindingProductImageForm = ProductImageFormBinding.inflate(layoutInflater)
            bindingProductImageForm.productImageFormUploadButton.setOnClickListener {
                val url = bindingProductImageForm.productImageFormEdittextImageUrl.text.toString()
                bindingProductImageForm.productImageFormImageview.tryLoadImage(url)
            }

            AlertDialog.Builder(this)
                .setView(bindingProductImageForm.root)
                .setPositiveButton("Save") { _, _ ->
                    url =
                        bindingProductImageForm.productImageFormEdittextImageUrl.text.toString()
                    binding.activityFormProductImage.tryLoadImage(url)
                }
                .setNegativeButton("Cancel") { _, _ ->
                    url = null
                }
                .show()
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
        return Product(name, description, price, url)
    }
}