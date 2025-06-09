package example.app.presentation.model

data class Budget(
    val name: String,
    val budget: Float,
    val spent: Float,
    val isExpense : Boolean,
    val currency: String = "EGP"
)
