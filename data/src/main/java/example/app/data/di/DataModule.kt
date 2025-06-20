package example.app.data.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.app.IBudgetRepository
import example.app.data.dao.TransactionDao
import example.app.data.repo.TransactionRepository
import example.app.ITransactionRepository
import example.app.data.dao.BudgetDao
import example.app.data.repo.BudgetRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): ITransactionRepository {
        return TransactionRepository(transactionDao)
    }

    @Provides
    @Singleton
    fun provideBudgetRepo(
        budgetDao: BudgetDao
    ): IBudgetRepository {
        return BudgetRepository(budgetDao)
    }
}
