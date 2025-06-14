package example.app.data.api

import example.app.data.model.CurrenciesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("list")
    suspend fun getAvailableCurrencies(
        @Query("access_key") accessKey: String
    ): Response<CurrenciesResponseModel>
}
