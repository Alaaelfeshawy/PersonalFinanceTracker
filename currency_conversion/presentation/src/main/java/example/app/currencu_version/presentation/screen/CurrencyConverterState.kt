package example.app.currencu_version.presentation.screen

import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.base.ui.ViewState
import example.app.currencu_version.presentation.model.ExchangeRateUiModel

data class CurrencyConverterState(
    val exchangeRateState: UIState<ExchangeRateUiModel> = UIState.Initial,
    val amount : Double ?=null,
    val selectedCurrency : Map<String,Double>?=null
):ViewState

sealed class CurrencyConverterEvent: UIEvent {
    data object GetExchangeRate : CurrencyConverterEvent()
    data class SetAmount(val amount : Double) : CurrencyConverterEvent()
}