package example.app.currencu_version.presentation.model

import androidx.compose.runtime.Stable
import example.app.currency_conversion.domain.model.CurrenciesDomainModel

@Stable
data class ExchangeRateUiModel(
    val id : Long?=null,
    val success: Boolean,
    val currencies: Map<String,String>,
)

fun CurrenciesDomainModel.toUI() : ExchangeRateUiModel {
    return ExchangeRateUiModel(
        id = id,
        success= success,
        currencies = currencies
    )
}