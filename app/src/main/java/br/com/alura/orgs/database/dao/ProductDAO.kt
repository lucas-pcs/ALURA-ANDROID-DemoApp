package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.orgs.ui.model.Product

@Dao
interface ProductDAO {

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getProductById(id: Long): Product?

    @Query("SELECT * FROM Product")
    fun getProductList(): List<Product>

    @Insert
    fun addProduct(product: Product)

    @Update
    fun updateProduct(newProduct: Product)
    @Delete
    fun removeProduct(product: Product)

}