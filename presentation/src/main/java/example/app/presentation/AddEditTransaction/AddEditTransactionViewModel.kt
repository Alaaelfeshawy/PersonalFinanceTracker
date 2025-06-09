package example.app.presentation.AddEditTransaction

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.domain.di.dispatchers.qualifiers.IODispatcher
import example.app.domain.usecase.AddTransactionUseCase
import example.app.presentation.base.BaseViewModel
import example.app.presentation.base.UIEvent
import example.app.presentation.base.UIState
import example.app.presentation.model.TransactionUi
import example.app.presentation.model.toCategory
import example.app.presentation.model.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val addTransactionUseCase : AddTransactionUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
): BaseViewModel<AddEditTransactionState,AddEditTransactionEvents>(coroutineDispatcher){

    override fun createInitialState(): AddEditTransactionState = AddEditTransactionState()

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent){
            is AddEditTransactionEvents.SetID -> {
                setState {
                    copy(
                        transactionUi = transactionUi.copy(id = uiEvent.id)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateAmount -> {
                setState {
                    copy(
                        transactionUi = transactionUi.copy(amount = uiEvent.amount)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateCategory -> {
                setState {
                    copy(
                        transactionUi = transactionUi.copy(category = uiEvent.category)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateDate -> {
                setState {
                    copy(
                        transactionUi = transactionUi.copy(date = convertMillisToDate(uiEvent.date))
                    )
                }
            }
            is AddEditTransactionEvents.UpdateNotes -> {
                setState {
                    copy(
                        transactionUi = transactionUi.copy(notes = uiEvent.notes)
                    )
                }
            }
            is AddEditTransactionEvents.UpdateType ->{
                setState {
                    copy(
                        transactionUi = transactionUi.copy(type = uiEvent.type)
                    )
                }
            }
            is AddEditTransactionEvents.SaveTransaction -> {
                saveTransaction()
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
                addTransactionUseCase(uiState.value.transactionUi.toDomain())
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


    private fun convertMillisToDate(millis: Long?): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return millis?.let { Date(it) }?.let { formatter.format(it) } ?: ""
    }
}