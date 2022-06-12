package com.cason.eatorgasm.module

import com.cason.eatorgasm.viewmodel.usecase.BoardUseCaseExecutor
import com.cason.eatorgasm.viewmodelimpl.usecase.BoardUseCaseExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BoardModule {
    @Binds
    abstract fun bindBoardUseCase(boardUseCaseExecutorImpl: BoardUseCaseExecutorImpl): BoardUseCaseExecutor
}