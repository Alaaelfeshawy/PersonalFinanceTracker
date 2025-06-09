package example.app.presentation.AddEditTransaction

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.domain.di.dispatchers.qualifiers.IODispatcher
import example.app.presentation.base.BaseViewModel
import example.app.presentation.base.UIEvent
import kotlinx.coroutines.CoroutineDispatcher
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
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
            is AddEditTransactionEvents.UpdateTitle -> {
                setState {
                    copy(
                        transactionUi = transactionUi.copy(title = uiEvent.title)
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

            }
        }
    }


    fun convertMillisToDate(millis: Long?): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return millis?.let { Date(it) }?.let { formatter.format(it) } ?: ""
    }
}