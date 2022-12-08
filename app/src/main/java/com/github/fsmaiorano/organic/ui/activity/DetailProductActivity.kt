package com.github.fsmaiorano.organic.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.fsmaiorano.organic.R
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityDetailProductBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage
import com.github.fsmaiorano.organic.helpers.CurrencyHelper
import com.github.fsmaiorano.organic.model.Product

class DetailProductActivity : AppCompatActivity() {

    private lateinit var product: Product
    private val binding by lazy { ActivityDetailProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::product.isInitialized) {
            val db = AppDatabase.instance(this)
            val productDao = db.productDao()
            when (item.itemId) {
                R.id.menu_detail_product_edit -> {
                    Log.i("DetailProductActivity", "Edit")
                    Intent(this, FormProductActivity::class.java).apply {
                        putExtra("product", product)
                        startActivity(this)
                    }
                }

                R.id.menu_detail_product_delete -> {
                    Log.i("DetailProductActivity", "Delete")
                    productDao.delete(product)
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
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
            product = productData
            tryFillData(productData)
        }
    }

    private fun tryFillData(product: Product) {
        binding.apply {
            activityDetailProductImageview.tryLoadImage(product.imageUrl)
            activityDetailProductChipPrice.text = CurrencyHelper().formatCurrency(product.price)
            activityDetailProductTextviewName.text = product.name
            activityDetailProductTextviewDescription.text = product.description
        }
    }
}

