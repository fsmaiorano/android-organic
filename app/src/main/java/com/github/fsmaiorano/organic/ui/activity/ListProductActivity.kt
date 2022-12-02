package com.github.fsmaiorano.organic.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.fsmaiorano.organic.dao.ProductDao
import com.github.fsmaiorano.organic.databinding.ActivityListProductBinding

class ListProductActivity : AppCompatActivity() {
    private val dao = ProductDao()
    private val binding by lazy { ActivityListProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFab()
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
}

