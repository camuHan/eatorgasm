package com.cason.eatorgasm.viewmodel.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.model.entity.EatUserProfileItem
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val handle: SavedStateHandle
    , private val repository: FirestoreRepository
    , private val usecase: FetchMyProfileUseCaseExecutor): ViewModel() {

    fun loadUserData() {
        usecase.fetchProfileData()
    }

    fun getUserLiveData(): LiveData<EatUserProfileItem?>? {
        return usecase.getUserInfoLiveData()
    }
}