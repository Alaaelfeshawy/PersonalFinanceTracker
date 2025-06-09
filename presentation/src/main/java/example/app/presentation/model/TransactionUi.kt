package example.app.presentation.model

import example.app.domain.model.TransactionDomainModel

data class TransactionUi(
    val id: Long?=null,
    val amount: String?=null,
    val category: CategoryUIModel?=null,
    val date: String?=null,
    val notes: String? = null,
    val type: TransactionType?=null,
)

fun TransactionUi.toDomain():TransactionDomainModel{
    return TransactionDomainModel(
        id = id,
        amount = amount,
        category = category?.toCategory()?.toDomain(),
        type = type?.toDomain(),
        date =date,
        note =notes
    )
}

fun TransactionDomainModel.toUI():TransactionUi{
    return TransactionUi(
        id = id,
        amount = amount,
        category = category?.toUi()?.toUIModel(),
        type = type?.toUi(),
        date =date,
        notes = note,
    )
}

