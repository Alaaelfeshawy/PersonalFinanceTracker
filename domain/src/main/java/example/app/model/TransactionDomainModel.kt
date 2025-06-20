package example.app.model

data class TransactionDomainModel(
    val id: Long?,
    val amount: String?,
    val category: CategoryDomainModel?,
    val type: TransactionTypeDomainModel?,
    val date: String?,
    val note: String?
)