package example.app.data.api.datasource

import example.app.data.api.validate.ApiResult
import example.app.data.model.CurrenciesResponseModel


interface IRemoteDataSource {

    suspend fun getAvailableCurrencies(): ApiResult<CurrenciesResponseModel>
}