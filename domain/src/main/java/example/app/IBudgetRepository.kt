package example.app

import example.app.model.BudgetDomainModel
import kotlinx.coroutines.flow.Flow

interface IBudgetRepository {
    fun getBudgets(): Flow<List<BudgetDomainModel>>
    suspend fun setBudget(budget: BudgetDomainModel)
}
