package example.app.currency_conversion.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.app.currency_conversion.domain.ports.ExchangeRateProviderPort
import example.app.currency_conversion.domain.usecase.GetAvailableCurrenciesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetExchangeRateUseCase(exchangeRateProvider: ExchangeRateProviderPort): GetAvailableCurrenciesUseCase {
        return GetAvailableCurrenciesUseCase(exchangeRateProvider)
    }
}
