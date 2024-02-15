package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProductDAO
import br.com.alura.orgs.databinding.ActivityProductFormBinding
import br.com.alura.orgs.ui.model.Product
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity() {

    private val productFormBinding by lazy {
        ActivityProductFormBinding.inflate(layoutInflater)
    }
    private val productDAO = ProductDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(productFormBinding.root)
        configureSaveButton()
        val imageButton = productFormBinding.activityProductFormImage
        imageButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setView(R.layout.product_form_imageload)
                .setPositiveButton("Confirm") { _, _ -> }
                .setNegativeButton("Cancel") { _, _->}
                .show()
        }

    }

    private fun configureSaveButton() {
        val saveButton = productFormBinding.activityProductFormSaveButton
        saveButton.setOnClickListener {
            val newProduct = createNewProduct()
            productDAO.addProduct(newProduct)
            finish()
        }
    }

    private fun createNewProduct(): Product {
        val fieldName = productFormBinding.activityProductFormTextinputedittextName
        val name = fieldName.text.toString()

        val fieldDescription = productFormBinding.activityProductFormTextinputedittextDescription
        val description = fieldDescription.text.toString()

        val fieldValue = productFormBinding.activityProductFormTextinputedittextValue
        val valueInText = fieldValue.text.toString()

        val value = if (valueInText.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valueInText)
        }

        return Product(
            name = name,
            description = description,
            value = value
        )
    }
}