package example.app.presentation.home

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.base.ui.BaseViewModel
import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.di.qualifiers.IODispatcher
import example.app.usecase.GetAllTransactionsUseCase
import example.app.usecase.RemoveTransactionUseCase
import example.app.presentation.model.TransactionUi
import example.app.presentation.model.toDomain
import example.app.presentation.model.toUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTransactionsUseCase : GetAllTransactionsUseCase,
    private val removeTransactionUseCase : RemoveTransactionUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
): BaseViewModel<HomeState, HomeEvents>(coroutineDispatcher){

    override fun createInitialState(): HomeState = HomeState()

    override fun handleEvent(uiEvent: HomeEvents) {
      when(uiEvent){
          is HomeEvents.RemoveTransaction -> {
              removeTransaction(uiEvent.model)
          }
          is HomeEvents.GetTransactions -> {
              getAllTransactions()
          }
      }
    }

    private fun getAllTransactions(){
        launchCoroutineScope {
            getAllTransactionsUseCase()
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
                    val response = domain.map {
                        it.toUI()
                    }
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
                setState {
                    copy(
                        transactionState = UIState.Success(data =null)
                    )
                }
            } catch (e: Exception) {
                setState {
                    copy(
                        transactionState = UIState.Error(e.message.orEmpty())
                    )
                }
            }
        }    }

}