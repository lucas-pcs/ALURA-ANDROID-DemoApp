package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val fieldName = productFormBinding.activityProductFormName
        val name = fieldName.text.toString()

        val fieldDescription = productFormBinding.activityProductFormDescription
        val description = fieldDescription.text.toString()

        val fieldValue = productFormBinding.activityProductFormValue
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