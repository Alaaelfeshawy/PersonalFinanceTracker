package example.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import example.app.ITransactionRepository
import example.app.usecase.AddTransactionUseCase
import example.app.usecase.EditTransactionUseCase
import example.app.usecase.GetAllTransactionsUseCase
import example.app.usecase.GetTransactionUseCase
import example.app.usecase.RemoveTransactionUseCase

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

    @Provides
    fun provideGetTransactionUseCase(repository: ITransactionRepository): GetTransactionUseCase {
        return GetTransactionUseCase(repository)
    }
}
