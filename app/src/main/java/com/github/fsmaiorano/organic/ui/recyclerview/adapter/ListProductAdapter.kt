package com.github.fsmaiorano.organic.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.fsmaiorano.organic.databinding.ProductItemBinding
import com.github.fsmaiorano.organic.model.Product
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListProductAdapter(private val context: Context, products: List<Product>) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val name: TextView = binding.productItemName
            name.text = product.name
            val description: TextView = binding.productItemDescription
            description.text = product.description
            val price: TextView = binding.productItemPrice
            price.text = formatCurrency(product.price)
        }

        private fun formatCurrency(value: BigDecimal): String {
            val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "pt"))
            return formatter.format(value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun update(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

}