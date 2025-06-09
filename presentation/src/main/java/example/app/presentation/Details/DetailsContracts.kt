package example.app.presentation.Details

import example.app.presentation.base.UIEvent
import example.app.presentation.base.UIState
import example.app.presentation.base.ViewState
import example.app.presentation.model.TransactionUi

data class DetailsState(
    val transactionState : UIState<TransactionUi> = UIState.Initial,
): ViewState

sealed class DetailsEvents : UIEvent {
    data class RemoveTransaction(val model : TransactionUi) :DetailsEvents()
    data class GetTransactions(val id : Long?) :DetailsEvents()
}