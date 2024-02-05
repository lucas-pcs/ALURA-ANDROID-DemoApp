package br.com.alura.orgs.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProductDAO
import br.com.alura.orgs.ui.model.Product
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val productDAO = ProductDAO()
        val productList: List<Product> = productDAO.getProductList()

        val productListRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        productListRecyclerView.adapter = ProductListAdapter(context = this, productList = productList)
        productListRecyclerView.layoutManager = LinearLayoutManager(this)

        val fabCreateForm = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fabCreateForm.setOnClickListener{
            val intentGoToForm = Intent(this, ProductFormActivity::class.java)
            startActivity(intentGoToForm)
        }
    }
}