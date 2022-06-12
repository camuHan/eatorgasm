package com.cason.eatorgasm.module

import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import com.cason.eatorgasm.viewmodelimpl.usecase.FetchMyProfileUseCaseExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PrivateModule {
    @Singleton
    @Binds
    abstract fun bindMyProfileUseCase(fetchMyProfileUsecaseExecutorImpl: FetchMyProfileUseCaseExecutorImpl): FetchMyProfileUseCaseExecutor
}