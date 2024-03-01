package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.ui.model.Product

@Dao
interface ProductDAO {

    @Query("SELECT * FROM Product")
    fun getProductList(): List<Product>

    @Insert
    fun addProduct(product: Product)

    @Delete
    fun removeProduct(product: Product)

}