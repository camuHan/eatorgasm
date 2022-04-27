package com.cason.eatorgasm.viewmodel.screen

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.EditProfileView
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val handle: SavedStateHandle
    , private val repository: FirestoreRepository
    , private val usecase: FetchMyProfileUseCaseExecutor): ViewModel(), EditProfileView.ActionListener {

    private lateinit var mActivityRef: WeakReference<Activity>
    private lateinit var editProfileView: EditProfileView

    fun setParentContext(parentContext: Activity) {
        mActivityRef = WeakReference(parentContext)
    }


    fun setView(view: EditProfileView) {
        view.setActionListener(this)
        editProfileView = view
    }

    fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>) {
        editProfileView.setUserProfileLiveData(liveData)
    }

    fun loadUserData() {
//        usecase.loadUserData()
    }

    fun requestSignOut() {
//        usecase.signOut()
    }

    override fun onRenderToast(msg: String?) {
        TODO("Not yet implemented")
    }
}