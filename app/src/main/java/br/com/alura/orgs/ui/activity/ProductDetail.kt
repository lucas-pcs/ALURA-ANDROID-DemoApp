package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductDetailBinding
import br.com.alura.orgs.ui.extensions.loadImage
import br.com.alura.orgs.ui.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductDetail : AppCompatActivity() {
    private val productDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private val productDAO by lazy {
        AppDatabase.getDBInstance(this).productDAO()
    }

    private lateinit var product: Product
    private var productID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productDetailBinding.root)

        val productReceived =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("product", Product::class.java)
            } else {
                intent.getParcelableExtra<Product>("product")
            }

        productReceived?.let {
            this.product = it
            this.productID = it.id
        } ?: finish()

    }

    override fun onResume() {
        super.onResume()
        productID?.let {
            val productById = productDAO.getProductById(it)
            fillProductDetailScreen(productById)
        }
    }

    private fun fillProductDetailScreen(product: Product?){
        product?.let {
            productDetailBinding.activityProductDetailImage.loadImage(it.imageURL)
            productDetailBinding.activityProductDetailName.text = it.name
            productDetailBinding.activityProductDetailDescription.text = it.description
            productDetailBinding.activityProductDetailValue.text =
                NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(it.value)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_detail_menu_edit -> {
                Intent(this, ProductFormActivity::class.java).apply {
                    putExtra("product",product)
                    startActivity(this)
                }
            }
            R.id.product_detail_menu_delete -> {
                product.let {
                    productDAO.removeProduct(it)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}