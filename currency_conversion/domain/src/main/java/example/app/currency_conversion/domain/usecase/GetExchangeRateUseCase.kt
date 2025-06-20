package example.app.currency_conversion.domain.usecase

import example.app.currency_conversion.domain.model.ExchangeRateDomainModel
import example.app.currency_conversion.domain.ports.ExchangeRateProviderPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val exchangeRateProvider: ExchangeRateProviderPort,
) {
    operator fun invoke(
        to : String,
        from : String,
        amount : String
    ): Flow<ExchangeRateDomainModel> {
        return  flow {
            emit(exchangeRateProvider.exchangeRate(
                to = to,
                from = from,
                amount = amount
            ))
        }
    }
}