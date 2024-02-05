package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProductDAO
import br.com.alura.orgs.ui.model.Product
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_form)

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener{
            val fieldName = findViewById<EditText>(R.id.name)
            val name = fieldName.text.toString()

            val fieldDescription = findViewById<EditText>(R.id.description)
            val description = fieldDescription.text.toString()

            val fieldValue = findViewById<EditText>(R.id.value)
            val valueInText = fieldValue.text.toString()

            val value = if(valueInText.isBlank()){
                BigDecimal.ZERO
            } else {
                BigDecimal(valueInText)
            }

            val newProduct = Product(
                name = name,
                description = description,
                value = value
            )

            Log.d("ProductForm", "on click save button new product: $newProduct")

            val productDAO = ProductDAO()
            val productList = productDAO.getProductList()
            productDAO.addProduct(newProduct)

            Log.d("ProductForm", "Product List: $productList")
            finish()
        }
    }
}