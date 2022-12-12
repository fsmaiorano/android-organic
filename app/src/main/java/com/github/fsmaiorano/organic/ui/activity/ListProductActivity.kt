package com.github.fsmaiorano.organic.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.github.fsmaiorano.organic.R
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityListProductBinding
import com.github.fsmaiorano.organic.extensions.goTo
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.ui.recyclerview.adapter.ListProductAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal


class ListProductActivity : BaseUserActivity() {
    private val adapter = ListProductAdapter(this)
    private val binding by lazy { ActivityListProductBinding.inflate(layoutInflater) }

    private val productDao by lazy {
        AppDatabase.instance(this).productDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setRecyclerView()
        setFab()
        setSeed()

        lifecycleScope.launch {
            launch {
                setSeed()
            }
            launch {
                user.filterNotNull().collect {
                    Log.i("ListProductActivity", "User: $it")
                    searchUserProducts()
                }
            }
        }
    }

    private suspend fun searchUserProducts() {
        lifecycleScope.launch {
            productDao.getAll().collect { products ->
                adapter.update(products)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_product, menu)
        menuInflater.inflate(R.menu.menu_profile_option, menu)
        menuInflater.inflate(R.menu.menu_app_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            executeMenuOption(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun executeMenuOption(item: MenuItem) {
        withContext(Dispatchers.IO) {
            val sortedProduct: List<Product>? =
                when (item.itemId) {
                    R.id.menu_list_product_order_by_name_desc ->
                        productDao.getAllOrderByNameDesc()
                    R.id.menu_list_product_order_by_name_asc ->
                        productDao.getAllOrderByNameAsc()
                    R.id.menu_list_product_order_by_description_desc ->
                        productDao.getAllOrderByDescriptionDesc()
                    R.id.menu_list_product_order_by_description_asc ->
                        productDao.getAllOrderByDescriptionAsc()
                    R.id.menu_list_product_order_by_price_desc ->
                        productDao.getAllOrderByPriceDesc()
                    R.id.menu_list_product_order_by_price_asc ->
                        productDao.getAllOrderByPriceAsc()
                    R.id.menu_list_product_order_by_category ->
                        productDao.getAllOrderByNameDesc()
                    R.id.menu_app_options_logout -> {
                        lifecycleScope.launch {
                            logoutUser()
                        }
                        null
                    }
                    R.id.menu_profile_option -> {
                        goTo(ProfileActivity::class.java)
                        null
                    }

                    else -> null
                }
            sortedProduct?.let {
                adapter.update(it)
            }
        }
    }

    private fun setFab() {
        binding.activityListProductFabButton.setOnClickListener {
            goTo(FormProductActivity::class.java)
        }
    }

    private fun setRecyclerView() {
        val recyclerview = binding.activityListProductRecyclerView
        recyclerview.adapter = adapter
        adapter.onProductClick = { product ->
            goTo(DetailProductActivity::class.java) {
                putExtra("productId", product.id)
            }
        }
    }

    private fun setSeed() {
        var settings = getSharedPreferences("PREFS_NAME", 0)
        val seedLoaded = settings.getBoolean("SEED_APPLIED", false)
        if (!seedLoaded) {
            settings = getSharedPreferences("PREFS_NAME", 0)
            val editor = settings.edit()
            editor.putBoolean("SEED_APPLIED", true)
            editor.apply()

            lifecycleScope.launch() {
                productDao.save(
                    Product(
                        name = "Salad",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sem nibh, suscipit nec posuere venenatis, placerat et quam. Nunc non enim vel sapien vehicula sodales sit amet ut tortor. Fusce consequat dictum mollis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean accumsan felis eget libero interdum, non viverra tortor consequat. Morbi sed est rhoncus justo iaculis dapibus id mattis tortor. Praesent commodo metus at vehicula sagittis. Nullam tortor sapien, aliquet nec nibh id, accumsan dictum turpis. In placerat dapibus augue tincidunt bibendum. Proin accumsan odio ut feugiat convallis. Praesent vel ligula vitae ante feugiat malesuada ut vel tellus. Aenean tempus elit a elit dictum, eu cursus erat dictum. Nullam scelerisque, lacus eget dictum condimentum, libero orci elementum ligula, quis pretium ipsum libero at leo. Etiam magna mauris, tempus ut egestas at, dapibus eu magna. Curabitur nec risus elementum, ornare lectus vitae, ultricies libero. Etiam vulputate tempus lorem, vitae mattis mi congue sit amet.",
                        price = BigDecimal(4.50),
                        imageUrl = "https://img.freepik.com/fotos-gratis/salada-de-tomate-pepino-cebola-roxa-e-folhas-de-alface-menu-de-vitamina-verao-saudavel-comida-vegetal-vegana-mesa-de-jantar-vegetariana-vista-do-topo-configuracao-plana_2829-6482.jpg?w=2000&t=st=1670080595~exp=1670081195~hmac=73cd5ac619cea294df58610c3a05ddd39729de4b2f847af3d36b40ebbdbabc48"
                    ),
                )
                productDao.save(
                    Product(
                        name = "Fruit salad",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sem nibh, suscipit nec posuere venenatis, placerat et quam. Nunc non enim vel sapien vehicula sodales sit amet ut tortor. Fusce consequat dictum mollis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean accumsan felis eget libero interdum, non viverra tortor consequat. Morbi sed est rhoncus justo iaculis dapibus id mattis tortor. Praesent commodo metus at vehicula sagittis. Nullam tortor sapien, aliquet nec nibh id, accumsan dictum turpis. In placerat dapibus augue tincidunt bibendum. Proin accumsan odio ut feugiat convallis. Praesent vel ligula vitae ante feugiat malesuada ut vel tellus. Aenean tempus elit a elit dictum, eu cursus erat dictum. Nullam scelerisque, lacus eget dictum condimentum, libero orci elementum ligula, quis pretium ipsum libero at leo. Etiam magna mauris, tempus ut egestas at, dapibus eu magna. Curabitur nec risus elementum, ornare lectus vitae, ultricies libero. Etiam vulputate tempus lorem, vitae mattis mi congue sit amet.",
                        price = BigDecimal(5.99),
                        imageUrl = "https://s2.glbimg.com/teAj4kXl8Hdol1P4C1jZvb0623g=/0x0:1280x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_1f540e0b94d8437dbbc39d567a1dee68/internal_photos/bs/2021/N/q/mW7w0wSz6ZW7HyMUaPkQ/salada-de-frutas-receita.jpg"
                    ),
                )
                productDao.save(
                    Product(
                        name = "Vegan chilli",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sem nibh, suscipit nec posuere venenatis, placerat et quam. Nunc non enim vel sapien vehicula sodales sit amet ut tortor. Fusce consequat dictum mollis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean accumsan felis eget libero interdum, non viverra tortor consequat. Morbi sed est rhoncus justo iaculis dapibus id mattis tortor. Praesent commodo metus at vehicula sagittis. Nullam tortor sapien, aliquet nec nibh id, accumsan dictum turpis. In placerat dapibus augue tincidunt bibendum. Proin accumsan odio ut feugiat convallis. Praesent vel ligula vitae ante feugiat malesuada ut vel tellus. Aenean tempus elit a elit dictum, eu cursus erat dictum. Nullam scelerisque, lacus eget dictum condimentum, libero orci elementum ligula, quis pretium ipsum libero at leo. Etiam magna mauris, tempus ut egestas at, dapibus eu magna. Curabitur nec risus elementum, ornare lectus vitae, ultricies libero. Etiam vulputate tempus lorem, vitae mattis mi congue sit amet.",
                        price = BigDecimal(5.99),
                        imageUrl = "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/vegan-chilli-be48585.jpg?quality=90&webp=true&resize=440,400"
                    ),
                )
                productDao.save(
                    Product(
                        name = "Carrots",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sem nibh, suscipit nec posuere venenatis, placerat et quam. Nunc non enim vel sapien vehicula sodales sit amet ut tortor. Fusce consequat dictum mollis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean accumsan felis eget libero interdum, non viverra tortor consequat. Morbi sed est rhoncus justo iaculis dapibus id mattis tortor. Praesent commodo metus at vehicula sagittis. Nullam tortor sapien, aliquet nec nibh id, accumsan dictum turpis. In placerat dapibus augue tincidunt bibendum. Proin accumsan odio ut feugiat convallis. Praesent vel ligula vitae ante feugiat malesuada ut vel tellus. Aenean tempus elit a elit dictum, eu cursus erat dictum. Nullam scelerisque, lacus eget dictum condimentum, libero orci elementum ligula, quis pretium ipsum libero at leo. Etiam magna mauris, tempus ut egestas at, dapibus eu magna. Curabitur nec risus elementum, ornare lectus vitae, ultricies libero. Etiam vulputate tempus lorem, vitae mattis mi congue sit amet.",
                        price = BigDecimal(5.99),
                        imageUrl = "https://static1.conquistesuavida.com.br/articles//4/90/04/@/27096-cada-cor-de-cenoura-oferece-um-beneficio-article_block_media_large-2.jpg"
                    ),
                )
            }
        }
    }
}



