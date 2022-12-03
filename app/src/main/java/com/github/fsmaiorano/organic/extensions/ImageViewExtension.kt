package com.github.fsmaiorano.organic.extensions

import android.widget.ImageView
import coil.load
import com.github.fsmaiorano.organic.R

fun ImageView.tryLoadImage(url: String? = null){
    load(url) {
        placeholder(R.drawable.placeholder)
        fallback(R.drawable.error)
        error(R.drawable.error)
    }
}