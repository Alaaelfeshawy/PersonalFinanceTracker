package example.app.currency_conversion.domain.ports

import example.app.currency_conversion.domain.model.CurrenciesDomainModel

interface ExchangeRateProviderPort {

    suspend fun getAvailableCurrencies(): CurrenciesDomainModel
}