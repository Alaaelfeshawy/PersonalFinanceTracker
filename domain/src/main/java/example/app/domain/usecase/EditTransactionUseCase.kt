package example.app.domain.usecase

import example.app.domain.ITransactionRepository
import example.app.domain.model.TransactionDomainModel

class EditTransactionUseCase(
    private val repository: ITransactionRepository
) {
    suspend operator fun invoke(transaction: TransactionDomainModel) {
        repository.editTransaction(transaction)
    }
}
