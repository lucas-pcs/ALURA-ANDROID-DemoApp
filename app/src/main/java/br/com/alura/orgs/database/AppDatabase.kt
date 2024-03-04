package br.com.alura.orgs.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.dao.ProductDAO
import br.com.alura.orgs.ui.model.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDAO(): ProductDAO

    companion object{
        @Volatile
        private lateinit var db: AppDatabase
        fun getDBInstance(context: Context): AppDatabase {
            if(::db.isInitialized) return db
            return Room.databaseBuilder(context, AppDatabase::class.java, "orgs.db")
                .build().also {
                    db = it
                    Log.d("AppDatabase", "getDBInstance: db instance created")
                }
        }
    }

}