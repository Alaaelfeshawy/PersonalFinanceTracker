package example.app.presentation.addEditTransaction

import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.base.ui.ViewState
import example.app.presentation.model.CategoryUIModel
import example.app.presentation.model.TransactionType
import example.app.presentation.model.TransactionUi

data class AddEditTransactionState(
    val transactionState : UIState<TransactionUi> = UIState.Initial,
    val transactionUi: TransactionUi?=TransactionUi(),
    val options : List<TransactionType> = listOf(
        TransactionType.INCOME,
        TransactionType.EXPENSE,
    )
): ViewState {

    fun isSaveButtonEnabled(): Boolean {
        val currentTransaction = transactionUi ?: return false
        val isDateValid = !currentTransaction.date.isNullOrEmpty()
        val isAmountValid = currentTransaction.amount?.let { amountString ->
            amountString.isNotEmpty() && (amountString.toDoubleOrNull() ?: 0.0) > 0.0
        } ?: false
        val isCategorySelected = currentTransaction.category != null
        val isTypeSelected = currentTransaction.type != null
        return isDateValid && isAmountValid && isCategorySelected && isTypeSelected
    }
}

sealed class AddEditTransactionEvents : UIEvent {
    data class UpdateTransactionDetails(
        val amount: String? = null,
        val category: CategoryUIModel? = null,
        val date: Long? = null,
        val notes: String? = null,
        val type: TransactionType? = null
    ) : AddEditTransactionEvents()
    data object SaveTransaction :AddEditTransactionEvents()
    data class GetTransactions(val id : Long?) :AddEditTransactionEvents()
}