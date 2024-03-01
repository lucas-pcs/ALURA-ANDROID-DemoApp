package br.com.alura.orgs.ui.activity

import android.os.Bundle
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
    private val productDAO by lazy {
        AppDatabase.getDBInstance(this).productDAO()
    }

    private var url: String? = null
    private var productID: Long = 0L
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Register new product"
        setContentView(productFormBinding.root)
        configureSaveButton()
        configureImageButton()
        getProduct()
        editNonNullProduct()
    }

    private fun getProduct(){
        productID = intent.getLongExtra(KEY_PRODUCT_ID, 0L)
        product = productDAO.getProductById(productID)
    }
    private fun editNonNullProduct(){
        product?.let {
            title = "Edit product"
            productID = it.id
            url = it.imageURL
            loadProductInfoOnScreen(it)
        }
    }

    private fun loadProductInfoOnScreen(product: Product){
        productFormBinding.activityProductFormImage.loadImage(product.imageURL)
        productFormBinding.activityProductFormTextName.setText(product.name)
        productFormBinding.activityProductFormTextDescription.setText(product.description)
        productFormBinding.activityProductFormTextValue.setText(product.value.toPlainString())
        productFormBinding.activityProductFormSaveButton.text = "Update Product"
    }

    private fun configureImageButton(){
        val imageButton = productFormBinding.activityProductFormImage
        imageButton.setOnClickListener {
            ProductFormImageDialog(this).showImageDialog(url) {
                    imageURL: String? ->  url = imageURL
                productFormBinding.activityProductFormImage.loadImage(url)
            }
        }
    }

    private fun configureSaveButton() {
        val saveButton = productFormBinding.activityProductFormSaveButton
        saveButton.setOnClickListener {
            val newProduct = createNewProduct()
            val productDAO = AppDatabase.getDBInstance(this).productDAO()

//            if(productID > 0) {
//                productDAO.updateProduct(newProduct)
//            } else {
//                productDAO.addProduct(newProduct)
//            }
            productDAO.addProduct(newProduct)
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
            id = productID,
            name = name,
            description = description,
            value = value,
            imageURL = url
        )
    }
}