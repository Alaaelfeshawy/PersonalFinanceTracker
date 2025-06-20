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
import example.app.presentation.shared.NavEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _navigate: MutableSharedFlow<NavEvent> = MutableSharedFlow()
    val navigate = _navigate.asSharedFlow()

    override fun createInitialState(): AddEditTransactionState = AddEditTransactionState()

    override fun handleEvent(uiEvent: AddEditTransactionEvents) {
        when (uiEvent){
            is AddEditTransactionEvents.SaveTransaction -> {
                saveTransaction()
            }

            is AddEditTransactionEvents.GetTransactions -> {
                getTransaction(uiEvent.id)
            }

            is AddEditTransactionEvents.UpdateTransactionDetails -> {
                setState {
                    copy(
                        transactionUi = transactionUi?.copy(amount = uiEvent.amount ?: transactionUi.amount,
                            category = uiEvent.category ?: transactionUi.category,
                            date = uiEvent.date?.let { convertMillisToDate(it) } ?: transactionUi.date,
                            notes = uiEvent.notes ?: transactionUi.notes,
                            type = uiEvent.type ?: transactionUi.type
                        )
                    )
                }
            }
        }
    }

    private fun saveTransaction() {
        launchCoroutineScope{
            val request =  uiState.value.transactionUi?.toDomain() ?: return@launchCoroutineScope
            addTransactionUseCase(request)
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
                }.collectLatest {
                    _navigate.emit(NavEvent.Navigate)
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