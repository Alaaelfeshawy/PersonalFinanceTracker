package example.app.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import example.app.domain.ITransactionRepository
import example.app.domain.usecase.AddTransactionUseCase
import example.app.domain.usecase.EditTransactionUseCase
import example.app.domain.usecase.GetAllTransactionsUseCase
import example.app.domain.usecase.RemoveTransactionUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllTransactionsUseCase(repository: ITransactionRepository): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCase(repository)
    }

    @Provides
    fun provideAddTransactionUseCase(repository: ITransactionRepository): AddTransactionUseCase {
        return AddTransactionUseCase(repository)
    }

    @Provides
    fun provideEditTransactionUseCase(repository: ITransactionRepository): EditTransactionUseCase {
        return EditTransactionUseCase(repository)
    }

    @Provides
    fun provideRemoveTransactionUseCase(repository: ITransactionRepository): RemoveTransactionUseCase {
        return RemoveTransactionUseCase(repository)
    }
}
