package br.com.alura.orgs.ui

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.alura.orgs.R
import br.com.alura.orgs.ui.model.Product
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import java.math.BigDecimal

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productList: List<Product> = listOf(
            Product("banana", "banana desc", BigDecimal("19.99")),
            Product("maça", "maça desc", BigDecimal("29.99"))
        )

        val productListRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        productListRecyclerView.adapter = ProductListAdapter(context = this, productList = productList)
        productListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}