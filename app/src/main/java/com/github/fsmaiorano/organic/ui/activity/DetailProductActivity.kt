package com.github.fsmaiorano.organic.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.fsmaiorano.organic.databinding.ActivityDetailProductBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage
import com.github.fsmaiorano.organic.model.Product

class DetailProductActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        tryLoadProduct()
    }

    private fun tryLoadProduct() {
        val productData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("product")
        }

        if (productData == null) {
            finish()
        } else {
            tryFillData(productData)
        }
    }

    private fun tryFillData(product: Product) {
        binding.apply {
            activityDetailProductImageview.tryLoadImage(product.imageUrl)
            activityDetailProductName.text = product.name
        }
    }
}

