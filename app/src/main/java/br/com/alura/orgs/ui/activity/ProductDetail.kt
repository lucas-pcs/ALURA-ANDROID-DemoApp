package br.com.alura.orgs.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.database.dao.ProductDAO
import br.com.alura.orgs.databinding.ActivityProductDetailBinding
import br.com.alura.orgs.ui.extensions.loadImage
import br.com.alura.orgs.ui.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductDetail : AppCompatActivity() {
    private val productDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }

    private lateinit var productDAO: ProductDAO
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productDetailBinding.root)

        productDAO = AppDatabase.getDBInstance(this).productDAO()

        val product =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("product")
        }

        product?.let {
            this.product = product
            productDetailBinding.activityProductDetailImage.loadImage(it.imageURL)
            productDetailBinding.activityProductDetailName.text = it.name
            productDetailBinding.activityProductDetailDescription.text = it.description
            productDetailBinding.activityProductDetailValue.text = NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(it.value)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.product_detail_menu_edit -> {

            }
            R.id.product_detail_menu_delete -> {
                productDAO.removeProduct(product)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}