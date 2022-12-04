package com.github.fsmaiorano.organic.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.github.fsmaiorano.organic.dao.ProductDao
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityListProductBinding
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.ui.recyclerview.adapter.ListProductAdapter
import java.math.BigDecimal

class ListProductActivity : AppCompatActivity() {
    private val dao = ProductDao()
    private val adapter = ListProductAdapter(this, dao.getAll())
    private val binding by lazy { ActivityListProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setFab()

        val db =
            Room.databaseBuilder(this, AppDatabase::class.java, "organic.db")
                .allowMainThreadQueries()
                .build()

        val productDao = db.productDao()
        productDao.insert(Product(name = "Banana", description = "Fruit", price = BigDecimal(2.5)))

        adapter.update(productDao.getAll())

    }

    override fun onResume() {
        super.onResume()
//        adapter.update(dao.getAll())
    }

    private fun setFab() {
        binding.activityListProductFabButton.setOnClickListener {
            goToFormProductActivity()
        }
    }

    private fun goToFormProductActivity() {
        val intent = Intent(this, FormProductActivity::class.java)
        startActivity(intent)
    }

    private fun goToDetailProductActivity(product: Product) {
        val intent = Intent(this, DetailProductActivity::class.java).apply {
            putExtra("product", product)
        }
        startActivity(intent)
    }

    private fun setRecyclerView() {
        val recyclerview = binding.activityListProductRecyclerView
        recyclerview.adapter = adapter
        adapter.onProductClick = { product ->
            goToDetailProductActivity(product)
        }
    }
}

