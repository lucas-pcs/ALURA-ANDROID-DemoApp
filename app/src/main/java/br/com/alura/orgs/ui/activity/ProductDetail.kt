package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductDetailBinding
import br.com.alura.orgs.ui.extensions.loadImage
import br.com.alura.orgs.ui.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class ProductDetail : AppCompatActivity() {
    private val productDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private val productDAO by lazy {
        AppDatabase.getDBInstance(this).productDAO()
    }

//    private val scope by lazy {
//        CoroutineScope(Dispatchers.Main)
//    }

    private var product: Product? = null
    private var productID: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productDetailBinding.root)

        // load productID from product list activity
        productID = intent.getLongExtra(KEY_PRODUCT_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        fillProductDetailScreen()
    }

    private fun fillProductDetailScreen(){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                product = productDAO.getProductById(productID)
            }
            product?.let {
                productDetailBinding.activityProductDetailImage.loadImage(it.imageURL)
                productDetailBinding.activityProductDetailName.text = it.name
                productDetailBinding.activityProductDetailDescription.text = it.description
                productDetailBinding.activityProductDetailValue.text =
                    NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(it.value)
            } ?: finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_detail_menu_edit -> { goToFormActivity() }
            R.id.product_detail_menu_delete -> { deleteProduct() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToFormActivity(){
        Intent(this, ProductFormActivity::class.java).apply {
            putExtra(KEY_PRODUCT_ID, product?.id)
            startActivity(this)
        }
    }
    private fun deleteProduct(){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                product?.let {
                    productDAO.removeProduct(it)
                }
            }
            finish()
        }
    }
}