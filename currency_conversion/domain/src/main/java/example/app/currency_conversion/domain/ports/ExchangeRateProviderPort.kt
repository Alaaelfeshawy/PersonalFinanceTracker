package example.app.currency_conversion.domain.ports

import example.app.currency_conversion.domain.model.CurrenciesDomainModel
import example.app.currency_conversion.domain.model.ExchangeRateDomainModel
import kotlin.jvm.Throws

interface ExchangeRateProviderPort {

    @Throws(Exception::class)
    suspend fun getAvailableCurrencies(): CurrenciesDomainModel

    @Throws(Exception::class)
    suspend fun exchangeRate(
        from : String,
        to :String,
        amount : String
    ): ExchangeRateDomainModel
}