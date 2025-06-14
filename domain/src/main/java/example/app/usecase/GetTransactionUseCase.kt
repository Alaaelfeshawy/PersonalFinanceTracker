package example.app.usecase

import example.app.ITransactionRepository
import example.app.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTransactionUseCase (
    private val repository: ITransactionRepository
) {
    suspend operator fun invoke(id: Long?) : Flow<TransactionDomainModel?> {
       return flow { emit(repository.getTransaction(id)) }
    }
}
