package br.com.alura.orgs.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.orgs.databinding.ActivityProductDetailBinding
import br.com.alura.orgs.ui.extensions.loadImage
import br.com.alura.orgs.ui.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductDetail : AppCompatActivity() {
    private val productDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(productDetailBinding.root)
        supportActionBar?.hide()

        val product: Product? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("product")
        }

        product?.let {
            productDetailBinding.activityProductDetailImage.loadImage(it.imageURL)
            productDetailBinding.activityProductDetailName.text = it.name
            productDetailBinding.activityProductDetailDescription.text = it.description
            productDetailBinding.activityProductDetailValue.text = NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(it.value)
        }

    }
}