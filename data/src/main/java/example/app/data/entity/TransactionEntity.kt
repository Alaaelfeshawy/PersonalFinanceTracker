package example.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import example.app.domain.model.CategoryDomainModel
import example.app.domain.model.TransactionDomainModel
import example.app.domain.model.TransactionTypeDomainModel

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val amount: String?,
    val category: String?,
    val type: String?,
    val date: String?,
    val note: String?
)

fun TransactionEntity.toDomain() = TransactionDomainModel(
    id = id,
    amount = amount,
    category = category?.let { CategoryDomainModel.valueOf(it) },
    type = type?.let { TransactionTypeDomainModel.valueOf(it) },
    date = date,
    note = note
)
fun TransactionDomainModel.toEntity() = TransactionEntity(
    id = id,
    amount = amount,
    category = category?.name,
    type = type?.name,
    date = date,
    note = note
)
