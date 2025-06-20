package example.app.presentation.budget

import java.math.BigDecimal

data class BudgetUIModel(
    val name: String,
    val budget: Float,
    val spent: BigDecimal,
    val currency: String = "EGP",
    val isExpense: Boolean
)
