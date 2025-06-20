package example.app.presentation.addEditTransaction

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.base.ui.BaseViewModel
import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.di.qualifiers.IODispatcher
import example.app.usecase.AddTransactionUseCase
import example.app.usecase.GetTransactionUseCase
import example.app.presentation.model.toDomain
import example.app.presentation.model.toUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val getTransactionUseCase : GetTransactionUseCase,
    private val addTransactionUseCase : AddTransactionUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
): BaseViewModel<AddEditTransactionState, AddEditTransactionEvents>(coroutineDispatcher){

    override fun createInitialState(): AddEditTransactionState = AddEditTransactionState()

    override fun handleEvent(uiEvent: AddEditTransactionEvents) {
        when (uiEvent){
            is AddEditTransactionEvents.UpdateAmount -> {
                setState {
                    copy(
                        transactionUi = transactionUi?.copy(amount = uiEvent.amount)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateCategory -> {
                setState {
                    copy(
                        transactionUi = transactionUi?.copy(category = uiEvent.category)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateDate -> {
                setState {
                    copy(
                        transactionUi = transactionUi?.copy(date = convertMillisToDate(uiEvent.date))
                    )
                }
            }
            is AddEditTransactionEvents.UpdateNotes -> {
                setState {
                    copy(
                        transactionUi = transactionUi?.copy(notes = uiEvent.notes)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateType ->{
                setState {
                    copy(
                        transactionUi = transactionUi?.copy(type = uiEvent.type)
                    )
                }
            }
            is AddEditTransactionEvents.SaveTransaction -> {
                saveTransaction()
            }

            is AddEditTransactionEvents.GetTransactions -> {
                getTransaction(uiEvent.id)
            }
        }
    }

    private fun saveTransaction() {
        launchCoroutineScope{
            setState {
                copy(
                    transactionState = UIState.Loading
                )
            }
            try {
                uiState.value.transactionUi?.toDomain()?.let { addTransactionUseCase(it) }
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
                            transactionState = UIState.Success(response),
                            transactionUi = response
                        )
                    }
                }
        }
    }

    private fun convertMillisToDate(millis: Long?): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return millis?.let { Date(it) }?.let { formatter.format(it) } ?: ""
    }
}