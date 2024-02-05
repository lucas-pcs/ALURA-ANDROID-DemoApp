package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProductDAO
import br.com.alura.orgs.ui.model.Product
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_form)
        configureSaveButton()
    }

    private fun configureSaveButton() {
        val saveButton = findViewById<Button>(R.id.activity_product_form_save_button)
        val productDAO = ProductDAO()
        saveButton.setOnClickListener {
            val newProduct = createNewProduct()
            productDAO.addProduct(newProduct)
            finish()
        }
    }

    private fun createNewProduct(): Product {
        val fieldName = findViewById<EditText>(R.id.activity_product_form_name)
        val name = fieldName.text.toString()

        val fieldDescription = findViewById<EditText>(R.id.activity_product_form_description)
        val description = fieldDescription.text.toString()

        val fieldValue = findViewById<EditText>(R.id.activity_product_form_value)
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