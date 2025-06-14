package example.app.data.api.datasource

import example.app.data.api.ExchangeRateApi
import example.app.data.api.validate.ApiResult
import example.app.data.api.validate.IValidateAPIResponse
import example.app.data.model.CurrenciesResponseModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val iValidateAPIResponse: IValidateAPIResponse,
    private val api: ExchangeRateApi
) : IRemoteDataSource {

    override suspend fun getAvailableCurrencies(): ApiResult<CurrenciesResponseModel> {
        return  iValidateAPIResponse.validateResponse(api.getAvailableCurrencies("b08b11043cf90f102c5596a287c87389"))
    }
}