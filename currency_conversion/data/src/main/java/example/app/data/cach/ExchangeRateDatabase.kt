package example.app.data.cach

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import example.app.data.model.CurrenciesEntity

@Database(
    entities = [CurrenciesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GsonTypeConverter::class)
 abstract class ExchangeRateDatabase : RoomDatabase() {
    abstract fun exchangeRateDao(): ExchangeRateDao
}