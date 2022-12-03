package com.github.fsmaiorano.organic.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.fsmaiorano.organic.databinding.ProductImageFormBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage

class FormImageDialog(private val context: Context) {
    fun show(defaultImageUrl: String? = null, whenImageLoaded: (imageUrl: String) -> Unit) {
        ProductImageFormBinding.inflate(LayoutInflater.from(context)).apply {
            defaultImageUrl?.let {
                productImageFormImageview.tryLoadImage(it)
                productImageFormEdittextImageUrl.setText(it)
            }

            productImageFormUploadButton.setOnClickListener {
                val url = productImageFormEdittextImageUrl.text.toString()
                productImageFormImageview.tryLoadImage(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Save") { _, _ ->
                    val url = productImageFormEdittextImageUrl.text.toString()
                    whenImageLoaded(url)
                }
                .setNegativeButton("Cancel") { _, _ ->

                }
                .show()
        }
    }
}