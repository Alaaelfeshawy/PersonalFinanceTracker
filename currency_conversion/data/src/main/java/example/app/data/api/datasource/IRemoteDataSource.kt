package example.app.data.api.datasource

import example.app.data.api.validate.ApiResult
import example.app.data.model.CurrenciesResponseModel
import example.app.data.model.ExchangeRateResponseModel

interface IRemoteDataSource {

    suspend fun getAvailableCurrencies(): ApiResult<CurrenciesResponseModel>

    suspend fun exchangeRate(
        from : String,
        to :String,
        amount : String
    ): ApiResult<ExchangeRateResponseModel>
}