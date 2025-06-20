package example.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import example.app.model.CategoryDomainModel
import example.app.model.TransactionDomainModel
import example.app.model.TransactionTypeDomainModel

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val amount: String?,
    val category: String?,
    val type: String?,
    val timestamp: Long?,
    val note: String?
)

fun TransactionEntity.toDomain() = TransactionDomainModel(
    id = id,
    amount = amount,
    category = category?.let { CategoryDomainModel.valueOf(it) },
    type = type?.let { TransactionTypeDomainModel.valueOf(it) },
    timestamp = timestamp,
    note = note
)
fun TransactionDomainModel.toEntity() = TransactionEntity(
    id = id,
    amount = amount,
    category = category?.name,
    type = type?.name,
    timestamp = timestamp,
    note = note
)
