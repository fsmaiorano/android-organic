package com.github.fsmaiorano.organic.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityFormProductBinding
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.ui.dialog.FormImageDialog
import java.math.BigDecimal


class FormProductActivity : AppCompatActivity() {
    private var idProduct: Long? = 0L
    private var product: Product? = null
    private var url: String? = null
    private val binding by lazy { ActivityFormProductBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }

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

        val productData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("product")
        }

        if (productData != null) {
            title = "Edit Product"
            idProduct = productData.id
            productSearch()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun productSearch() {
        idProduct?.let { id ->
            productDao.getById(id)?.let { storedProduct ->
                product = storedProduct
            }

            product?.let {
                idProduct = product!!.id
                url = product!!.imageUrl
                binding.activityFormProductEdittextName.setText(product!!.name)
                binding.activityFormProductEdittextDescription.setText(product!!.description)
                binding.activityFormProductEdittextPrice.setText(
                    product!!.price.toPlainString()
                )
                binding.activityFormProductImage.load(product!!.imageUrl)
            } ?: finish()
        }
    }

    private fun setSaveButton() {
        val btnSave = binding.activityFormProductButtonSave
        btnSave.setOnClickListener {
            val newProduct = createProduct()
            if (idProduct != 0L) {
                productDao.update(
                    newProduct
                )
            } else {
                productDao.insert(
                    newProduct
                )
            }
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
        return Product(
            id = idProduct ?: 0L,
            name = name,
            description = description,
            price = price,
            imageUrl = url
        )
    }
}