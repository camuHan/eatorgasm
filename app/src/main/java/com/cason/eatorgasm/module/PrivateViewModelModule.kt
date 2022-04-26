package com.cason.eatorgasm.module

import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.cason.eatorgasm.viewmodel.usecase.LoginUsecaseExecutor
import com.cason.eatorgasm.viewmodelimpl.usecase.LoginUsecaseExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MyPageModule {
    @Binds
    abstract fun bindFireStoreRepository(firestoreRepositoryImpl: FirestoreRepositoryImpl): FirestoreRepository

    @Binds
    abstract fun bindMyProfileUseCase(loginUsecaseExecutorImpl: LoginUsecaseExecutorImpl): LoginUsecaseExecutor
}