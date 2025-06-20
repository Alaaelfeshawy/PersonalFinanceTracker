package example.app.currencu_version.presentation.model

import androidx.compose.runtime.Stable
import example.app.currency_conversion.domain.model.CurrenciesDomainModel

@Stable
data class CurrenciesUIModel(
    val id : Long?=null,
    val success: Boolean,
    val currencies: Map<String,String>,
)

fun CurrenciesDomainModel.toUI() : CurrenciesUIModel {
    return CurrenciesUIModel(
        id = id,
        success= success,
        currencies = currencies
    )
}