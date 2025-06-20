package example.app

import example.app.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {
    fun getAllTransactions(): Flow<List<TransactionDomainModel>>
    suspend fun addTransaction(transaction: TransactionDomainModel)
    suspend fun removeTransaction(transaction: TransactionDomainModel)
    suspend fun editTransaction(transaction: TransactionDomainModel)
    suspend fun getTransaction(id: Long?): TransactionDomainModel?
}
