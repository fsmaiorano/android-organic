package com.github.fsmaiorano.organic.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val imageUrl: String? = null
) : Parcelable