package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.database.dao.ProductDAO
import br.com.alura.orgs.databinding.ActivityProductListBinding
import br.com.alura.orgs.ui.model.Product
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import java.math.BigDecimal

class ProductListActivity : AppCompatActivity() {
    private lateinit var productDAO: ProductDAO
    private val adapterProductList = ProductListAdapter(context = this)
    private val productListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productListBinding.root)

        configureRecyclerView()
        configureFAB()
        productDAO = AppDatabase.getDBInstance(this).productDAO()


    }

    override fun onResume() {
        super.onResume()
        adapterProductList.productList = productDAO.getProductList()
        adapterProductList.productLongClickListener =  { product, view ->
            val popupMenu = PopupMenu(this, view)
            val menuInflater = popupMenu.menuInflater
            menuInflater.inflate(R.menu.product_detail_menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.product_detail_menu_edit -> { true }
                    R.id.product_detail_menu_delete -> { deleteProduct(product) }
                    else -> true
                }
            }
        }
        adapterProductList.refreshList(productDAO.getProductList())
    }

    private fun deleteProduct(product: Product): Boolean{
        productDAO.removeProduct(product)
        adapterProductList.refreshList(productDAO.getProductList())
        return true
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
        adapterProductList.productClickListener = { product: Product ->
            val goToProductDetail = Intent(this@ProductListActivity, ProductDetail::class.java)
            goToProductDetail.putExtra(KEY_PRODUCT_ID, product.id)
            startActivity(goToProductDetail)
        }
        productListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}