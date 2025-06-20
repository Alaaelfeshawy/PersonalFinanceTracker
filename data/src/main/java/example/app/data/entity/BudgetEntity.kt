package example.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import example.app.model.BudgetDomainModel
import example.app.model.CategoryDomainModel

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey val name: String,
    val limit: Float,
    val isExpense: Boolean
)

fun BudgetEntity.toDomain() = BudgetDomainModel(
    name =  name.let { CategoryDomainModel.valueOf(it) },
    limit = limit,
    isExpense = isExpense,
)
fun BudgetDomainModel.toEntity() = BudgetEntity(
    name = name.name,
    limit = limit,
    isExpense = isExpense,
)
