package com.github.fsmaiorano.organic.helpers

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class CurrencyHelper {
    fun formatCurrency(value: BigDecimal): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "pt"))
        return formatter.format(value)
    }
}