package example.app.presentation.model

import example.app.model.TransactionDomainModel

data class TransactionUi(
    val id: Long?=null,
    val amount: String?=null,
    val category: CategoryUIModel?=null,
    val date: String?=null,
    val timestamp: Long?=null,
    val notes: String? = null,
    val type: TransactionType?=null,
)

fun TransactionUi.toDomain(): TransactionDomainModel {
    return TransactionDomainModel(
        id = id,
        amount = amount,
        category = category?.toCategory()?.toDomain(),
        type = type?.toDomain(),
        note = notes,
        timestamp = timestamp
    )
}

fun TransactionDomainModel.toUI():TransactionUi{
    return TransactionUi(
        id = id,
        amount = amount,
        category = category?.toUi()?.toUIModel(),
        type = type?.toUi(),
        timestamp =timestamp,
        notes = note,
    )
}

