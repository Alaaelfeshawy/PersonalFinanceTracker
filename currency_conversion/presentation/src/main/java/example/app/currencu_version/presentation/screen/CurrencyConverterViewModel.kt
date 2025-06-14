package example.app.currencu_version.presentation.screen

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.base.ui.BaseViewModel
import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.currency_conversion.domain.qualifiers.IODispatcher
import example.app.currency_conversion.domain.usecase.GetAvailableCurrenciesUseCase
import example.app.currencu_version.presentation.model.toUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val getAvailableCurrenciesUseCase: GetAvailableCurrenciesUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,

    ) : BaseViewModel<CurrencyConverterState, CurrencyConverterEvent>(coroutineDispatcher){

    override fun createInitialState(): CurrencyConverterState = CurrencyConverterState()

    override fun handleEvent(uiEvent: UIEvent) {
      when(uiEvent){
          is CurrencyConverterEvent.GetExchangeRate -> {
              getExchangeRate()
          }
          is CurrencyConverterEvent.SetAmount -> {
              setState {
                  copy(
                      amount = uiEvent.amount
                  )
              }
          }
      }
    }

    private fun getExchangeRate(){
        launchCoroutineScope {
            getAvailableCurrenciesUseCase()
                    .onStart {
                        setState {
                            copy(
                                exchangeRateState = UIState.Loading
                            )
                        }
                    }.catch {
                        setState {
                            copy(
                                exchangeRateState = UIState.Error(it.message.orEmpty())
                            )
                        }
                    }.collect { domain ->
                        val response = domain.toUI()
                        setState {
                            copy(
                                exchangeRateState = UIState.Success(response)
                            )
                        }
                    }
            }
    }
}