package com.cason.eatorgasm.module

import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.cason.eatorgasm.viewmodel.usecase.BoardUseCaseExecutor
import com.cason.eatorgasm.viewmodel.usecase.LoginUsecaseExecutor
import com.cason.eatorgasm.viewmodelimpl.usecase.BoardUseCaseExecutorImpl
import com.cason.eatorgasm.viewmodelimpl.usecase.LoginUsecaseExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BoardModule {
    @Binds
    abstract fun bindBoardUseCase(boardUsecaseExecutorImpl: BoardUseCaseExecutorImpl): BoardUseCaseExecutor
}