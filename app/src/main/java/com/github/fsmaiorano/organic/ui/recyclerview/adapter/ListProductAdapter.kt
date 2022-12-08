package com.github.fsmaiorano.organic.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.github.fsmaiorano.organic.R
import com.github.fsmaiorano.organic.databinding.ProductItemBinding
import com.github.fsmaiorano.organic.extensions.tryLoadImage
import com.github.fsmaiorano.organic.helpers.CurrencyHelper
import com.github.fsmaiorano.organic.model.Product

class ListProductAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var onProductClick: (product: Product) -> Unit = {},
    var onEditProductClick: (product: Product) -> Unit = {},
    var onDeleteProductClick: (product: Product) -> Unit = {},
) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onProductClick(product)
                }
            }

            itemView.setOnLongClickListener() {
                if (::product.isInitialized) {
                    PopupMenu(context, itemView).apply {
                        menuInflater.inflate(
                            R.menu.menu_detail_product,
                            menu
                        )
                        setOnMenuItemClickListener(this@ViewHolder)
                    }.show()
                }
                true
            }
        }

        fun bind(product: Product) {
            this.product = product

            val name: TextView = binding.productItemName
            name.text = product.name
            val description: TextView = binding.productItemDescription
            description.text = product.description
            val price: TextView = binding.productItemPrice
            price.text = CurrencyHelper().formatCurrency(product.price)

            val visibility = if (product.imageUrl != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.productItemImageView.visibility = visibility
            binding.productItemImageView.tryLoadImage(product.imageUrl)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_detail_product_edit -> onEditProductClick(product)
                    R.id.menu_detail_product_delete -> onDeleteProductClick(product)
                }
            }
            return true
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