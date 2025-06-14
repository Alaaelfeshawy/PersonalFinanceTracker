package example.app.currencu_version.presentation.screen

import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.base.ui.ViewState
import example.app.currencu_version.presentation.model.CurrenciesUIModel
import example.app.currencu_version.presentation.model.ExchangeRateUiModel

data class CurrencyConverterState(
    val currencyState: UIState<CurrenciesUIModel> = UIState.Initial,
    val exchangeRateState: UIState<ExchangeRateUiModel> = UIState.Initial,
    val amount : String ?= "1",
    val fromSelectedCurrency :String?=null,
    val toSelectedCurrency :String?=null,

    ):ViewState

sealed class CurrencyConverterEvent: UIEvent {
    data object GetAvailableCurrencies : CurrencyConverterEvent()
    data class SetAmount(val amount : String) : CurrencyConverterEvent()
    data class SetSelectedToCurrency(val currency : String) : CurrencyConverterEvent()
    data class SetSelectedFromCurrency(val currency : String) : CurrencyConverterEvent()
    data object OnSwapCurrencies : CurrencyConverterEvent()
}