package example.app.domain.usecase

import example.app.domain.ITransactionRepository
import example.app.domain.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUseCase(
    private val repository: ITransactionRepository
) {
    operator fun invoke(): Flow<List<TransactionDomainModel>> = repository.getAllTransactions()
}
