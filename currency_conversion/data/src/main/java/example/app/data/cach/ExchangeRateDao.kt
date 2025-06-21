package example.app.data.cach

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.app.data.model.CurrenciesEntity

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: CurrenciesEntity)

    @Query("SELECT * FROM currencies_table")
    suspend fun getRates(): CurrenciesEntity?

}