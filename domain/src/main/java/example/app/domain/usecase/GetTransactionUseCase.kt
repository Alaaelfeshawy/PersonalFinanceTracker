package example.app.domain.usecase

import example.app.domain.ITransactionRepository
import example.app.domain.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTransactionUseCase (
    private val repository: ITransactionRepository
) {
    suspend operator fun invoke(id: Long?) : Flow<TransactionDomainModel?> {
       return flow { emit(repository.getTransaction(id)) }
    }
}
