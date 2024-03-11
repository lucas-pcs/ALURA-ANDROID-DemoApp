package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.alura.orgs.ui.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getProductById(id: Long): Flow<Product?>

    @Query("SELECT * FROM Product")
    fun getProductList(): Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY name DESC")
    suspend fun getProductListByNameDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY name ASC")
    suspend fun getProductListByNameAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description DESC")
    suspend fun getProductListByDescDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY description ASC")
    suspend fun getProductListByDescAsc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY value DESC")
    suspend fun getProductListByValueDesc(): List<Product>

    @Query("SELECT * FROM Product ORDER BY value ASC")
    suspend fun getProductListByValueAsc(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(newProduct: Product)
    @Delete
    suspend fun removeProduct(product: Product)

}