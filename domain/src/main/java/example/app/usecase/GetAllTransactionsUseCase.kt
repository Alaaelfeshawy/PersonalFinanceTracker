package example.app.usecase

import example.app.ITransactionRepository
import example.app.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUseCase(
    private val repository: ITransactionRepository
) {
    operator fun invoke(): Flow<List<TransactionDomainModel>> = repository.getAllTransactions()
}
