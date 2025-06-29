package example.app.di.qualifiers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object DispatchersModule {
    @Provides
    @ViewModelScoped
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @ViewModelScoped
    @IODispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}