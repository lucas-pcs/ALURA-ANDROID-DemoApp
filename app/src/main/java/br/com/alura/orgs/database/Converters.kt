package br.com.alura.orgs.database

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun toDouble(bigDecimal: BigDecimal?): Double?{
        return bigDecimal?.let { bigDecimal.toDouble() }
    }

    @TypeConverter
    fun toBigDecimal(double: Double?): BigDecimal?{
        return double?.let { return BigDecimal(double.toString()) }
    }
}