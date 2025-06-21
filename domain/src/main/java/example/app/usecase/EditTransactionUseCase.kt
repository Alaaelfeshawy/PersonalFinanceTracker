package example.app.usecase

import example.app.ITransactionRepository
import example.app.model.TransactionDomainModel

class EditTransactionUseCase(
    private val repository: ITransactionRepository
) {
    suspend operator fun invoke(transaction: TransactionDomainModel) {
        repository.editTransaction(transaction)
    }
}
