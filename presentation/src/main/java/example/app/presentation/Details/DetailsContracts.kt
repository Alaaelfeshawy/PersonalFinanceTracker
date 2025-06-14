package example.app.presentation.Details

import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.base.ui.ViewState
import example.app.presentation.model.TransactionUi

data class DetailsState(
    val transactionState : UIState<TransactionUi> = UIState.Initial,
): ViewState

sealed class DetailsEvents : UIEvent {
    data class RemoveTransaction(val model : TransactionUi) :DetailsEvents()
    data class GetTransactions(val id : Long?) :DetailsEvents()
}