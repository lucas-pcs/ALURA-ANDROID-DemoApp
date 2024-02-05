package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProductDAO
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductListActivity : AppCompatActivity() {
    private val productDAO = ProductDAO()
    private val adapterProductList = ProductListAdapter(context = this, productList = productDAO.getProductList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        configureRecyclerView()
        configureFAB()
    }

    override fun onResume() {
        super.onResume()
        adapterProductList.refreshList(productDAO.getProductList())
    }

    private fun configureFAB() {
        val fabCreateForm = findViewById<FloatingActionButton>(R.id.activity_product_list_fab)
        fabCreateForm.setOnClickListener {
            val intentGoToForm = Intent(this, ProductFormActivity::class.java)
            startActivity(intentGoToForm)
        }
    }

    private fun configureRecyclerView() {
        val productListRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.activity_product_list_recycler_view)
        productListRecyclerView.adapter = adapterProductList
        productListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}