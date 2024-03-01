package br.com.alura.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.dao.ProductDAO
import br.com.alura.orgs.ui.model.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDAO(): ProductDAO

}