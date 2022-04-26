package com.cason.eatorgasm.module

import androidx.lifecycle.LifecycleOwner
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.view.PrivateView
import com.cason.eatorgasm.viewimpl.PrivateViewImpl
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import com.cason.eatorgasm.viewmodelimpl.usecase.FetchMyProfileUseCaseExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PrivateModule {
//    @Provides
//    fun providePrivteView(binding: PrivateFragmentBinding, lifecycleOwner: LifecycleOwner): PrivateView {
//        return PrivateView(binding, lifecycleOwner)
//    }

    @Binds
    abstract fun bindMyProfileUseCase(fetchMyProfileUsecaseExecutorImpl: FetchMyProfileUseCaseExecutorImpl): FetchMyProfileUseCaseExecutor
}