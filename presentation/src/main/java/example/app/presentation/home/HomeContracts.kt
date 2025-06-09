package example.app.presentation.home

import example.app.presentation.base.UIEvent
import example.app.presentation.base.UIState
import example.app.presentation.base.ViewState
import example.app.presentation.model.TransactionUi

data class HomeState(
    val transactionState : UIState<List<TransactionUi>> = UIState.Initial,
): ViewState

sealed class HomeEvents : UIEvent {
    data class RemoveTransaction(val model : TransactionUi) :HomeEvents()
    data object GetTransactions :HomeEvents()
}