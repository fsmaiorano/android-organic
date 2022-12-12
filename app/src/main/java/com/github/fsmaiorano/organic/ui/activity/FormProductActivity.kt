package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityFormProductBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.ui.dialog.FormImageDialog
import kotlinx.coroutines.launch
import java.math.BigDecimal


class FormProductActivity : AppCompatActivity() {
    private var productId: Long = 0L
    private var url: String? = null
    private val binding by lazy { ActivityFormProductBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }
    private val userDao by lazy { AppDatabase.instance(this).userDao() }

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

        tryLoadProduct()
    }

    override fun onResume() {
        super.onResume()
        trySearchProduct()
    }

    private fun tryLoadProduct() {
        productId = intent.getLongExtra("productId", 0L)
    }

    private fun trySearchProduct() {
        lifecycleScope.launch {
            if (productId != 0L) {
                productDao.getById(productId)?.collect { storedProduct ->
                    title = "Edit Product"
                    tryFillData(storedProduct)
                }
            }
        }
    }

    private fun setSaveButton() {
        val btnSave = binding.activityFormProductButtonSave
        btnSave.setOnClickListener {
            val newProduct = createProduct()
            lifecycleScope.launch {
                productDao.save(newProduct)
                finish()
            }
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
        return Product(
            id = productId,
            name = name,
            description = description,
            price = price,
            imageUrl = url
        )
    }

    private fun tryFillData(product: Product) {
        Log.i("DetailProductActivity", "product: $product | url: $url")
        url = product.imageUrl
        binding.apply {
            activityFormProductImage.tryLoadImage(product.imageUrl)
            activityFormProductEdittextPrice.setText(product.price.toPlainString())
            activityFormProductEdittextDescription.setText(product.description)
            activityFormProductEdittextName.setText(product.name)
        }
    }
}