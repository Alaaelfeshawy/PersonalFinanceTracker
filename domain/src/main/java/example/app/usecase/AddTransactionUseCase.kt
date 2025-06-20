package example.app.usecase

import example.app.ITransactionRepository
import example.app.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTransactionUseCase (
    private val repository: ITransactionRepository
) {
    operator fun invoke(transaction: TransactionDomainModel) : Flow<Unit> {
       return flow {  emit(repository.addTransaction(transaction)) }
    }
}
