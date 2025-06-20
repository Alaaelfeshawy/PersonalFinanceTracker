package example.app.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.app.currency_conversion.domain.ports.CurrencyDataStorePort
import example.app.currency_conversion.domain.ports.ExchangeRateProviderPort
import example.app.data.adapter.CurrencyDataStoreAdapter
import example.app.data.adapter.ExchangeRateApiAdapter
import example.app.data.api.ExchangeRateApi
import example.app.data.api.datasource.IRemoteDataSource
import example.app.data.api.datasource.RemoteDataSource
import example.app.data.api.validate.IValidateAPIResponse
import example.app.data.api.validate.ValidateAPIResponse
import example.app.data.cach.ExchangeRateDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyConversionDataModule {

    @Provides
    fun provideValidateAPIResponse(): IValidateAPIResponse {
        return ValidateAPIResponse()
    }
    @Provides
    fun provideCurrencyDataStoreAdapter(database: ExchangeRateDatabase): CurrencyDataStorePort {
        return CurrencyDataStoreAdapter(database)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        iValidateAPIResponse: IValidateAPIResponse,
        api: ExchangeRateApi
    ): IRemoteDataSource {
        return RemoteDataSource(iValidateAPIResponse, api)
    }

    @Provides
    @Singleton
    fun providesExchangeRateApiAdapter(
        dataSource: IRemoteDataSource,
        currencyDataStorePort : CurrencyDataStorePort
    ): ExchangeRateProviderPort {
        return ExchangeRateApiAdapter(dataSource,currencyDataStorePort)
    }
}