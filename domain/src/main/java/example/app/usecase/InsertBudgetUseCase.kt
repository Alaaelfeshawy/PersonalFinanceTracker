package example.app.usecase

import example.app.IBudgetRepository
import example.app.model.BudgetDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertBudgetUseCase (
    private val repository: IBudgetRepository
) {
    operator fun invoke(budget : BudgetDomainModel) : Flow<Unit> {
         return flow { emit(repository.setBudget(budget)) }
    }
}
