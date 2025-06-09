package example.app.presentation.AddEditTransaction

import example.app.presentation.base.UIEvent
import example.app.presentation.base.UIState
import example.app.presentation.base.ViewState
import example.app.presentation.model.CategoryUIModel
import example.app.presentation.model.TransactionType
import example.app.presentation.model.TransactionUi
import java.math.BigDecimal

data class AddEditTransactionState(
    val transactionState : UIState<TransactionUi> = UIState.Initial,
    val transactionUi: TransactionUi=TransactionUi(),
    val options : List<TransactionType> = listOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
    )
): ViewState {

    fun isSaveButtonEnabled (): Boolean {
        return !transactionUi.date.isNullOrEmpty()  &&
                (!transactionUi.amount.isNullOrEmpty() && transactionUi.amount.toDouble() > 0.0 )  &&
                transactionUi.category != null &&
                transactionUi.type != null
    }
}

sealed class AddEditTransactionEvents : UIEvent {
    data class SetID(val id : Long) :AddEditTransactionEvents()
    data class UpdateAmount(val amount : String?) :AddEditTransactionEvents()
    data class UpdateCategory(val category : CategoryUIModel) :AddEditTransactionEvents()
    data class UpdateDate(val date : Long?) :AddEditTransactionEvents()
    data class UpdateNotes(val notes : String) :AddEditTransactionEvents()
    data class UpdateType(val type: TransactionType) :AddEditTransactionEvents()
    data object SaveTransaction :AddEditTransactionEvents()
}