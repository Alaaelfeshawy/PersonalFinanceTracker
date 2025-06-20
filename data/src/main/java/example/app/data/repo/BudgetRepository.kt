package example.app.data.repo

import example.app.IBudgetRepository
import example.app.data.dao.BudgetDao
import example.app.data.entity.toDomain
import example.app.data.entity.toEntity
import example.app.model.BudgetDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
): IBudgetRepository {
    override fun getBudgets(): Flow<List<BudgetDomainModel>> {
        return budgetDao.getBudgets().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun setBudget(budget: BudgetDomainModel) {
        budgetDao.insertOrUpdateBudget(budget.toEntity())
    }

}
