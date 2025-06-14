package example.app.data.adapter

import example.app.currency_conversion.domain.model.CurrenciesDomainModel
import example.app.currency_conversion.domain.model.ExchangeRateDomainModel
import example.app.currency_conversion.domain.ports.CurrencyDataStorePort
import example.app.currency_conversion.domain.ports.ExchangeRateProviderPort
import example.app.data.api.datasource.IRemoteDataSource
import example.app.data.api.validate.ApiResult
import example.app.data.model.toDomain

class ExchangeRateApiAdapter (
   private val dataSource: IRemoteDataSource,
   private val currencyDataStorePort : CurrencyDataStorePort
) : ExchangeRateProviderPort {

    override suspend fun getAvailableCurrencies(): CurrenciesDomainModel  {
        try {
            val cached = currencyDataStorePort.getExchangeRate()
            if (cached != null) {
                return cached
            }

            when(val response = dataSource.getAvailableCurrencies()){
                is ApiResult.Error -> {
                    throw Exception(response.exception)
                }
                is ApiResult.Success -> {

                    currencyDataStorePort.saveExchangeRate(
                        CurrenciesDomainModel(
                            success = response.data.success ?: false,
                            currencies  = response.data.currencies ?: emptyMap(),
                        )
                    )
                    return currencyDataStorePort.getExchangeRate()!!
                }
            }

        } catch (e: Exception) {
           throw Exception(e)
        }
    }

    override suspend fun exchangeRate(
        from: String,
        to: String,
        amount: String
    ): ExchangeRateDomainModel {
       return when(val response = dataSource.exchangeRate(
           from, to, amount
       )){
            is ApiResult.Error -> {
                throw Exception(response.exception)
            }
            is ApiResult.Success -> {
                return response.data.toDomain()
            }
        }
    }
}