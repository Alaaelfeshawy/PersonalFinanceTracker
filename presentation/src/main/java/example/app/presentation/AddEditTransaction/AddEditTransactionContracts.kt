package example.app.presentation.AddEditTransaction

import example.app.presentation.Constants
import example.app.presentation.base.UIEvent
import example.app.presentation.base.UIState
import example.app.presentation.base.ViewState
import example.app.presentation.model.Category
import example.app.presentation.model.TransactionUi

data class AddEditTransactionState(
    val transactionState : UIState<TransactionUi> = UIState.Initial,
    val transactionUi: TransactionUi=TransactionUi(),
    val options : List<Constants.Type> = listOf(
        Constants.Type.INCOME,
        Constants.Type.EXPENSE,
    )
): ViewState {

    fun isSaveButtonEnabled (): Boolean {
        return !transactionUi.date.isNullOrEmpty()  &&
                !transactionUi.title.isNullOrEmpty() &&
                !transactionUi.amount.isNullOrEmpty()  &&
                transactionUi.category != null &&
                transactionUi.type != null
    }
}

sealed class AddEditTransactionEvents : UIEvent {
    data class SetID(val id : String) :AddEditTransactionEvents()
    data class UpdateTitle(val title : String) :AddEditTransactionEvents()
    data class UpdateAmount(val amount : String) :AddEditTransactionEvents()
    data class UpdateCategory(val category : Category) :AddEditTransactionEvents()
    data class UpdateDate(val date : Long?) :AddEditTransactionEvents()
    data class UpdateNotes(val notes : String) :AddEditTransactionEvents()
    data class UpdateType(val type: Constants.Type) :AddEditTransactionEvents()
    data object SaveTransaction :AddEditTransactionEvents()
}