package com.oliinyk.yaroslav.easyreads.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherIO

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherDefault

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppCoroutineScope

@Module
@InstallIn(SingletonComponent::class)
class CoroutineScopeModule {
    @Provides
    @AppCoroutineScope
    @Singleton
    fun provideCoroutineScope(
        @DispatcherDefault dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @DispatcherIO
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DispatcherDefault
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
