package example.app.presentation.budget

import example.app.base.ui.UIEvent
import example.app.base.ui.UIState
import example.app.base.ui.ViewState
import example.app.presentation.model.CategoryUIModel

data class BudgetState(
    val budgetState : UIState<List<BudgetUIModel>> = UIState.Initial,
    val selectedCategory : CategoryUIModel?=null,
    val budgetUIModel: BudgetUIModel?=null
): ViewState

sealed class BudgetEvents : UIEvent {
    data class UpdateBudget(val name: String, val limit: Float, val isExpense: Boolean) :
        BudgetEvents()
    data class UpdateCategory(val budget: CategoryUIModel) : BudgetEvents()
    data object LoadBudgetData : BudgetEvents()
}