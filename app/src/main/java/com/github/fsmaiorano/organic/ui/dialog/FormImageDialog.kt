package com.github.fsmaiorano.organic.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.fsmaiorano.organic.databinding.ProductImageFormBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage

class FormImageDialog(private val context: Context) {
    fun show(whenImageLoaded: (imageUrl: String) -> Unit) {
        val binding = ProductImageFormBinding.inflate(LayoutInflater.from(context))
        binding.productImageFormUploadButton.setOnClickListener {
            val url = binding.productImageFormEdittextImageUrl.text.toString()
            binding.productImageFormImageview.tryLoadImage(url)
        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                val url = binding.productImageFormEdittextImageUrl.text.toString()
                Log.i("FormImageDialog", url)
                whenImageLoaded(url)
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .show()
    }
}