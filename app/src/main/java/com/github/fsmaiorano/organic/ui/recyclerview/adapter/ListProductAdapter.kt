package com.github.fsmaiorano.organic.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.fsmaiorano.organic.databinding.ProductItemBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage
import com.github.fsmaiorano.organic.helpers.CurrencyHelper
import com.github.fsmaiorano.organic.model.Product
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListProductAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var onProductClick: (product: Product) -> Unit = {}
) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onProductClick(product)
                }
            }
        }

        fun bind(product: Product) {
            this.product = product

            val name: TextView = binding.productItemName
            name.text = product.name
            val description: TextView = binding.productItemDescription
            description.text = product.description
            val price: TextView = binding.productItemPrice
            price.text =  CurrencyHelper().formatCurrency(product.price)

            val visibility = if (product.imageUrl != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.productItemImageView.visibility = visibility
            binding.productItemImageView.tryLoadImage(product.imageUrl)
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