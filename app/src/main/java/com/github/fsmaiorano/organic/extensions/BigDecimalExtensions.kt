package com.github.fsmaiorano.organic.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.formatCurrency(): String {
    val formatter: NumberFormat = NumberFormat
        .getCurrencyInstance(Locale("pt", "pt"))
    return formatter.format(this)
}