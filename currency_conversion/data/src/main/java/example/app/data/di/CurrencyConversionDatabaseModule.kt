package example.app.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import example.app.data.cach.ExchangeRateDao
import example.app.data.cach.ExchangeRateDatabase
import example.app.data.cach.GsonTypeConverter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyConversionDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        gson: Gson
    ): ExchangeRateDatabase {
        return Room.databaseBuilder(
            context,
            ExchangeRateDatabase::class.java,
            "exchange_rates.db"
        )
            .addTypeConverter(GsonTypeConverter(gson))
            .addMigrations()
            .build()
    }

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideGsonTypeConverter(gson: Gson): GsonTypeConverter {
        return GsonTypeConverter(gson)
    }

    @Provides
    fun provideExchangeRateDao(database: ExchangeRateDatabase): ExchangeRateDao {
        return database.exchangeRateDao()
    }


}