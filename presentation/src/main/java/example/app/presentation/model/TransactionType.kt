package example.app.presentation.model

import example.app.domain.model.TransactionTypeDomainModel

enum class TransactionType(val value : String) {
    INCOME("Income"),
    EXPENSE("Expense")
}

fun TransactionTypeDomainModel.toUi(): TransactionType = TransactionType.entries[this.ordinal]

fun TransactionType.toDomain(): TransactionTypeDomainModel = TransactionTypeDomainModel.entries[this.ordinal]