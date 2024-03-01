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
    private val adapterProductList =
        ProductListAdapter(context = this, productList = productDAO.getProductList())
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
                value = BigDecimal("19.83"),
                imageURL = "https://images.pexels.com/photos/209339/pexels-photo-209339.jpeg"
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
        val productListRecyclerView: RecyclerView =
            productListBinding.activityProductListRecyclerView
        productListRecyclerView.adapter = adapterProductList
//        adapterProductList.productClickListener = object: ProductListAdapter.ProductClickListener {
//            override fun onProductClickListener(product: Product){
//                val goToProductDetail = Intent(this@ProductListActivity, ProductDetail::class.java)
//                goToProductDetail.putExtra("product",product)
//                startActivity(goToProductDetail)
//            }
//        }
        adapterProductList.productClickListener = { product: Product ->
            val goToProductDetail = Intent(this@ProductListActivity, ProductDetail::class.java)
            goToProductDetail.putExtra("product", product)
            startActivity(goToProductDetail)
        }
        productListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}