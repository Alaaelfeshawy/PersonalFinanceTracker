package example.app.presentation.home

import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.base.ui.ViewState
import example.app.presentation.model.TransactionUi

data class HomeState(
    val transactionState : UIState<List<TransactionUi>> = UIState.Initial,
): ViewState

sealed class HomeEvents : UIEvent {
    data class RemoveTransaction(val model : TransactionUi) :HomeEvents()
    data object GetTransactions :HomeEvents()
}