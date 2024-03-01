package br.com.alura.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductFormBinding
import br.com.alura.orgs.ui.dialog.ProductFormImageDialog
import br.com.alura.orgs.ui.extensions.loadImage
import br.com.alura.orgs.ui.model.Product
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity() {

    private val productFormBinding by lazy {
        ActivityProductFormBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var idReceived: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Register new product"
        setContentView(productFormBinding.root)
        configureSaveButton()

        val imageButton = productFormBinding.activityProductFormImage
        imageButton.setOnClickListener {
            ProductFormImageDialog(this).showImageDialog(url) {
                imageURL: String? ->  url = imageURL
                productFormBinding.activityProductFormImage.loadImage(url)
            }
        }

        val productReceived = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("product", Product::class.java)
        } else {
            intent.getParcelableExtra<Product>("product")
        }

        productReceived?.let {

            title = "Edit product"
            idReceived = it.id
            productFormBinding.activityProductFormImage.loadImage(it.imageURL)
            productFormBinding.activityProductFormTextName.setText(it.name)
            productFormBinding.activityProductFormTextDescription.setText(it.description)
            productFormBinding.activityProductFormTextValue.setText(it.value.toPlainString())
            productFormBinding.activityProductFormSaveButton.text = "Update Product"

            url = it.imageURL
        }



    }

    private fun configureSaveButton() {
        val saveButton = productFormBinding.activityProductFormSaveButton
        saveButton.setOnClickListener {
            val newProduct = createNewProduct()
            val productDAO = AppDatabase.getDBInstance(this).productDAO()

            if(idReceived > 0) {
                productDAO.updateProduct(newProduct)
            } else {
                productDAO.addProduct(newProduct)
            }
            finish()
        }
    }

    private fun createNewProduct(): Product {
        val fieldName = productFormBinding.activityProductFormTextName
        val name = fieldName.text.toString()

        val fieldDescription = productFormBinding.activityProductFormTextDescription
        val description = fieldDescription.text.toString()

        val fieldValue = productFormBinding.activityProductFormTextValue
        val valueInText = fieldValue.text.toString()

        val value = if (valueInText.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valueInText)
        }

        return Product(
            id = idReceived,
            name = name,
            description = description,
            value = value,
            imageURL = url
        )
    }
}