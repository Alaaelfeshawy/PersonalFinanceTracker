package example.app.presentation.model

import example.app.presentation.Constants

data class TransactionUi(
    val id: String?=null,
    val title : String?=null,
    val amount: String?=null,
    val category: Category?=null,
    val date: String?=null,
    val notes: String? = null,
    val type: Constants.Type?=null,
)

