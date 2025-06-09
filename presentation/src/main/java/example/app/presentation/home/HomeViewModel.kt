package example.app.presentation.home

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.domain.di.dispatchers.qualifiers.IODispatcher
import example.app.domain.usecase.GetAllTransactionsUseCase
import example.app.domain.usecase.RemoveTransactionUseCase
import example.app.presentation.base.BaseViewModel
import example.app.presentation.base.UIEvent
import example.app.presentation.base.UIState
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
): BaseViewModel<HomeState,HomeEvents>(coroutineDispatcher){

    override fun createInitialState(): HomeState = HomeState()

    override fun handleEvent(uiEvent: UIEvent) {
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