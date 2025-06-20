package example.app.data.adapter

import example.app.currency_conversion.domain.model.CurrenciesDomainModel
import example.app.currency_conversion.domain.ports.CurrencyDataStorePort
import example.app.data.cach.ExchangeRateDatabase
import example.app.data.model.toDomain
import example.app.data.model.toEntity
import javax.inject.Inject

class CurrencyDataStoreAdapter @Inject constructor(
    private val database: ExchangeRateDatabase
) : CurrencyDataStorePort {

    override suspend fun saveExchangeRate(rate: CurrenciesDomainModel) {
        database.exchangeRateDao().insertRates(rate.toEntity())
    }

    override suspend fun getExchangeRate(): CurrenciesDomainModel? {
        return database.exchangeRateDao()
            .getRates()
            ?.toDomain()
    }
}