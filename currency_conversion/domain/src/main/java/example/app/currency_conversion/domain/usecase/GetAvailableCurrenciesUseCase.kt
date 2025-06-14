package example.app.currency_conversion.domain.usecase

import example.app.currency_conversion.domain.model.CurrenciesDomainModel
import example.app.currency_conversion.domain.ports.ExchangeRateProviderPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAvailableCurrenciesUseCase @Inject constructor(
    private val exchangeRateProvider: ExchangeRateProviderPort,
) {
    suspend operator fun invoke(): Flow<CurrenciesDomainModel> {
        return  flowOf(exchangeRateProvider.getAvailableCurrencies())
    }
}