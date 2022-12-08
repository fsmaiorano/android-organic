package com.github.fsmaiorano.organic.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.fsmaiorano.organic.database.AppDatabase
import com.github.fsmaiorano.organic.databinding.ActivityListProductBinding
import com.github.fsmaiorano.organic.model.Product
import com.github.fsmaiorano.organic.ui.recyclerview.adapter.ListProductAdapter
import java.math.BigDecimal


class ListProductActivity : AppCompatActivity() {
    private val adapter = ListProductAdapter(this)
    private val binding by lazy { ActivityListProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setFab()
        setSeed()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val productDao = db.productDao()
        adapter.update(productDao.getAll())
        setSeed()
    }

    private fun setFab() {
        binding.activityListProductFabButton.setOnClickListener {
            goToFormProductActivity()
        }
    }

    private fun goToFormProductActivity() {
        val intent = Intent(this, FormProductActivity::class.java)
        startActivity(intent)
    }

    private fun goToDetailProductActivity(product: Product) {
        val intent = Intent(this, DetailProductActivity::class.java).apply {
            putExtra("product", product)
        }
        startActivity(intent)
    }

    private fun setRecyclerView() {
        val recyclerview = binding.activityListProductRecyclerView
        recyclerview.adapter = adapter
        adapter.onProductClick = { product ->
            goToDetailProductActivity(product)
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

            val db = AppDatabase.instance(this)
            val productDao = db.productDao()
            productDao.insert(
                Product(
                    name = "Salad",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sem nibh, suscipit nec posuere venenatis, placerat et quam. Nunc non enim vel sapien vehicula sodales sit amet ut tortor. Fusce consequat dictum mollis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean accumsan felis eget libero interdum, non viverra tortor consequat. Morbi sed est rhoncus justo iaculis dapibus id mattis tortor. Praesent commodo metus at vehicula sagittis. Nullam tortor sapien, aliquet nec nibh id, accumsan dictum turpis. In placerat dapibus augue tincidunt bibendum. Proin accumsan odio ut feugiat convallis. Praesent vel ligula vitae ante feugiat malesuada ut vel tellus. Aenean tempus elit a elit dictum, eu cursus erat dictum. Nullam scelerisque, lacus eget dictum condimentum, libero orci elementum ligula, quis pretium ipsum libero at leo. Etiam magna mauris, tempus ut egestas at, dapibus eu magna. Curabitur nec risus elementum, ornare lectus vitae, ultricies libero. Etiam vulputate tempus lorem, vitae mattis mi congue sit amet.",
                    price = BigDecimal(4.50),
                    imageUrl = "https://img.freepik.com/fotos-gratis/salada-de-tomate-pepino-cebola-roxa-e-folhas-de-alface-menu-de-vitamina-verao-saudavel-comida-vegetal-vegana-mesa-de-jantar-vegetariana-vista-do-topo-configuracao-plana_2829-6482.jpg?w=2000&t=st=1670080595~exp=1670081195~hmac=73cd5ac619cea294df58610c3a05ddd39729de4b2f847af3d36b40ebbdbabc48"
                ),
            )
            productDao.insert(
                Product(
                    name = "Fruit salad",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sem nibh, suscipit nec posuere venenatis, placerat et quam. Nunc non enim vel sapien vehicula sodales sit amet ut tortor. Fusce consequat dictum mollis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean accumsan felis eget libero interdum, non viverra tortor consequat. Morbi sed est rhoncus justo iaculis dapibus id mattis tortor. Praesent commodo metus at vehicula sagittis. Nullam tortor sapien, aliquet nec nibh id, accumsan dictum turpis. In placerat dapibus augue tincidunt bibendum. Proin accumsan odio ut feugiat convallis. Praesent vel ligula vitae ante feugiat malesuada ut vel tellus. Aenean tempus elit a elit dictum, eu cursus erat dictum. Nullam scelerisque, lacus eget dictum condimentum, libero orci elementum ligula, quis pretium ipsum libero at leo. Etiam magna mauris, tempus ut egestas at, dapibus eu magna. Curabitur nec risus elementum, ornare lectus vitae, ultricies libero. Etiam vulputate tempus lorem, vitae mattis mi congue sit amet.",
                    price = BigDecimal(5.99),
                    imageUrl = "https://s2.glbimg.com/teAj4kXl8Hdol1P4C1jZvb0623g=/0x0:1280x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_1f540e0b94d8437dbbc39d567a1dee68/internal_photos/bs/2021/N/q/mW7w0wSz6ZW7HyMUaPkQ/salada-de-frutas-receita.jpg"
                ),
            )
        }
    }
}

