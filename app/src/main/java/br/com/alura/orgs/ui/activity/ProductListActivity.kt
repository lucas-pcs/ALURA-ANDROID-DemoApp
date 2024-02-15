package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.dao.ProductDAO
import br.com.alura.orgs.databinding.ActivityProductListBinding
import br.com.alura.orgs.ui.model.Product
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import java.math.BigDecimal

class ProductListActivity : AppCompatActivity() {
    private val productDAO = ProductDAO()
    private val adapterProductList = ProductListAdapter(context = this, productList = productDAO.getProductList())
    private val productListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productListBinding.root)

        configureRecyclerView()
        configureFAB()

        productDAO.addProduct(
            Product(
                name = "Cesta de frutas",
                description = "Banana, maça e uva",
                value = BigDecimal("19.83")
            )
        )
    }

    override fun onResume() {
        super.onResume()
        adapterProductList.refreshList(productDAO.getProductList())
    }

    private fun configureFAB() {
        val fabCreateForm = productListBinding.activityProductListFab
        fabCreateForm.setOnClickListener {
            val intentGoToForm = Intent(this, ProductFormActivity::class.java)
            startActivity(intentGoToForm)
        }
    }

    private fun configureRecyclerView() {
        val productListRecyclerView: RecyclerView = productListBinding.activityProductListRecyclerView
        productListRecyclerView.adapter = adapterProductList
        productListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}