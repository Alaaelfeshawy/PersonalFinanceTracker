package example.app.currency_conversion.domain.ports

import example.app.currency_conversion.domain.model.CurrenciesDomainModel

interface CurrencyDataStorePort {
    suspend fun saveExchangeRate(rate: CurrenciesDomainModel)

    suspend fun getExchangeRate(): CurrenciesDomainModel?
}