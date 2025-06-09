package example.app.data.repo

import example.app.data.dao.TransactionDao
import example.app.data.entity.toDomain
import example.app.data.entity.toEntity
import example.app.domain.ITransactionRepository
import example.app.domain.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
): ITransactionRepository {
    override fun getAllTransactions(): Flow<List<TransactionDomainModel>> {
      return transactionDao.getAllTransactions().map { list ->
            list.map { it.toDomain() }
        }

    }

    override suspend fun addTransaction(transaction: TransactionDomainModel) =
        transactionDao.insertTransaction(transaction.toEntity())

    override suspend fun removeTransaction(transaction: TransactionDomainModel) =
        transactionDao.deleteTransaction(transaction.toEntity())

    override suspend fun editTransaction(transaction: TransactionDomainModel) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

   override suspend fun getTransaction(id: Long?): TransactionDomainModel?{
        return transactionDao.getTransactionById(id)?.toDomain()
    }
}
