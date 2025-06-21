package example.app.usecase

import example.app.IBudgetRepository
import example.app.model.BudgetDomainModel
import kotlinx.coroutines.flow.Flow

class GetBudgetListUseCase (
    private val repository: IBudgetRepository
) {
     operator fun invoke() : Flow<List<BudgetDomainModel>> {
       return repository.getBudgets()
    }
}
