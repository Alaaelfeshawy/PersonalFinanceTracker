package example.app.currencu_version.presentation.screen

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.base.ui.BaseViewModel
import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.currency_conversion.domain.qualifiers.IODispatcher
import example.app.currency_conversion.domain.usecase.GetAvailableCurrenciesUseCase
import example.app.currencu_version.presentation.model.toUI
import example.app.currency_conversion.domain.usecase.GetExchangeRateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val getAvailableCurrenciesUseCase: GetAvailableCurrenciesUseCase,
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,

    ) : BaseViewModel<CurrencyConverterState, CurrencyConverterEvent>(coroutineDispatcher){

    override fun createInitialState(): CurrencyConverterState = CurrencyConverterState()

    override fun handleEvent(uiEvent: CurrencyConverterEvent) {
        var needsExchangeRateUpdate = false

        when (uiEvent) {
            is CurrencyConverterEvent.GetAvailableCurrencies -> {
                getAvailableCurrencies()
            }
            is CurrencyConverterEvent.SetAmount -> {
                setState { copy(amount = uiEvent.amount) }
                needsExchangeRateUpdate = true
            }
            is CurrencyConverterEvent.SetSelectedFromCurrency -> {
                setState { copy(fromSelectedCurrency = uiEvent.currency) }
                needsExchangeRateUpdate = true
            }
            is CurrencyConverterEvent.SetSelectedToCurrency -> {
                setState { copy(toSelectedCurrency = uiEvent.currency) }
                needsExchangeRateUpdate = true
            }
            is CurrencyConverterEvent.OnSwapCurrencies -> {
                val currentFrom = uiState.value.fromSelectedCurrency
                val currentTo = uiState.value.toSelectedCurrency
                setState {
                    copy(
                        toSelectedCurrency = currentFrom,
                        fromSelectedCurrency = currentTo
                    )
                }
                needsExchangeRateUpdate = true
            }
        }

        if (needsExchangeRateUpdate) {
            getExchangeRate()
        }
    }

    private fun getExchangeRate() {
        launchCoroutineScope {
            getExchangeRateUseCase(
                to = uiState.value.toSelectedCurrency.orEmpty(),
                from =uiState.value.fromSelectedCurrency.orEmpty(),
                amount = uiState.value.amount.orEmpty()
            )
                .onStart {
                    setState {
                        copy(
                            exchangeRateState = UIState.Loading
                        )
                    }
                    delay(100)
                }.catch {
                    setState {
                        copy(
                            exchangeRateState = UIState.Error(it.message.orEmpty())
                        )
                    }
                }.collect { domain ->
                    val response = domain.toUI()
                    setState {
                        copy(exchangeRateState = UIState.Success(response))
                    }
                }
        }

    }

    private fun getAvailableCurrencies(){
        launchCoroutineScope {
            getAvailableCurrenciesUseCase()
                    .onStart {
                        setState {
                            copy(
                                currencyState = UIState.Loading
                            )
                        }
                    }.catch {
                        setState {
                            copy(
                                currencyState = UIState.Error(it.message.orEmpty())
                            )
                        }
                    }.collect { domain ->
                        val response = domain.toUI()
                        setState {
                            copy(
                                currencyState = UIState.Success(response),
                                fromSelectedCurrency = response.currencies.keys.toList().find { it == "EGP" },
                                toSelectedCurrency = response.currencies.keys.toList().find { it == "USD" },

                            )
                        }
                    }
            }
    }
}