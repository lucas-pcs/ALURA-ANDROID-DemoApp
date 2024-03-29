package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductListBinding
import br.com.alura.orgs.ui.model.Product
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {
    private val adapterProductList = ProductListAdapter(context = this)
    private val productDAO by lazy {
        AppDatabase.getDBInstance(this).productDAO()
    }
    private val productListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }
//    private val scope by lazy {
//        CoroutineScope(Dispatchers.Main)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productListBinding.root)
        configureRecyclerView()
        configureFAB()
        configurePopupMenu()

        lifecycleScope.launch {
            productDAO.getProductList().collect {
                adapterProductList.refreshList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        updateProductList(itemId = item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun updateProductList(itemId: Int) {
        var productListSorted: List<Product>? = null
        val handlerMenuSort = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            // TODO: use a more specific exception
            throw Exception("Menu Sort list exception")
        }
        lifecycleScope.launch(handlerMenuSort) {

            productListSorted = with(productDAO) {
                when (itemId) {
                    R.id.product_list_menu_name_asc -> {
                        this.getProductListByNameAsc()
                    }

                    R.id.product_list_menu_name_desc -> {
                        this.getProductListByNameDesc()
                    }

                    R.id.product_list_menu_desc_desc -> {
                        this.getProductListByDescDesc()
                    }

                    R.id.product_list_menu_desc_asc -> {
                        this.getProductListByDescAsc()
                    }

                    R.id.product_list_menu_value_desc -> {
                        this.getProductListByValueDesc()
                    }

                    R.id.product_list_menu_value_asc -> {
                        this.getProductListByValueAsc()
                    }

                    R.id.product_list_menu_noorder -> {
                        this.getProductList().collect { productListSorted = it }
                        productListSorted
                    }

                    else -> null
                }
            }

            productListSorted?.let {
                adapterProductList.refreshList(it)
            }
        }
    }

    private fun configurePopupMenu() {
        adapterProductList.productLongClickListener = { product, view ->
            val popupMenu = popupMenu(view)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.product_detail_menu_edit -> {
                        true
                    }

                    R.id.product_detail_menu_delete -> {
                        deleteProduct(product)
                    }

                    else -> true
                }
            }
        }
    }

    private fun popupMenu(view: View): PopupMenu {
        val popupMenu = PopupMenu(this, view)
        val menuInflater = popupMenu.menuInflater
        menuInflater.inflate(R.menu.product_detail_menu, popupMenu.menu)
        return popupMenu
    }

    private fun deleteProduct(product: Product): Boolean {
        lifecycleScope.launch {
            productDAO.removeProduct(product)

            productDAO.getProductList().collect {
                adapterProductList.refreshList(it)
            }
        }
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