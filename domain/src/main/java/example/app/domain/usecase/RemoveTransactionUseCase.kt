package example.app.domain.usecase

import example.app.domain.ITransactionRepository
import example.app.domain.model.TransactionDomainModel

class RemoveTransactionUseCase(
    private val repository: ITransactionRepository
) {
    suspend operator fun invoke(transaction: TransactionDomainModel) {
        repository.removeTransaction(transaction)
    }
}
