package com.github.fsmaiorano.organic.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val imageUrl: String? = null,
    var userId: String? = null
) : Parcelable {
    @IgnoredOnParcel
    @Ignore
    val priceIsValid = price > BigDecimal.ZERO
}
