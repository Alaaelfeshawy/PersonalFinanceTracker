package example.app.model

data class BudgetDomainModel(
    val name: CategoryDomainModel,
    val limit: Float,
    val isExpense: Boolean
)
