package example.app.presentation.details

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.base.ui.BaseViewModel
import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.di.qualifiers.IODispatcher
import example.app.usecase.GetTransactionUseCase
import example.app.usecase.RemoveTransactionUseCase
import example.app.presentation.model.TransactionUi
import example.app.presentation.model.toDomain
import example.app.presentation.model.toUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getTransactionUseCase : GetTransactionUseCase,
    private val removeTransactionUseCase : RemoveTransactionUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
): BaseViewModel<DetailsState, DetailsEvents>(coroutineDispatcher){

    override fun createInitialState(): DetailsState = DetailsState()

    private val _navigate: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val navigate = _navigate.asStateFlow()

    override fun handleEvent(uiEvent: DetailsEvents) {
      when(uiEvent){
          is DetailsEvents.RemoveTransaction -> {
              removeTransaction(uiEvent.model)
          }
          is DetailsEvents.GetTransactions -> {
              getTransaction(uiEvent.id)
          }
      }
    }

    private fun getTransaction(id : Long?){
        launchCoroutineScope {
            getTransactionUseCase(id)
                .onStart {
                setState {
                    copy(
                        transactionState = UIState.Loading
                    )
                }
                }.catch {
                    setState {
                        copy(
                            transactionState = UIState.Error(it.message.orEmpty())
                        )
                    }
                }.collectLatest { domain ->
                    val response = domain?.toUI()

                    setState {
                        copy(
                            transactionState = UIState.Success(response)
                        )
                    }
            }
        }
    }

    private fun removeTransaction(model : TransactionUi){
        launchCoroutineScope{
            setState {
                copy(
                    transactionState = UIState.Loading
                )
            }
            try {
                removeTransactionUseCase(model.toDomain())
                _navigate.value = true
            } catch (e: Exception) {
                setState {
                    copy(
                        transactionState = UIState.Error(e.message.orEmpty())
                    )
                }
            }
        }
    }

}