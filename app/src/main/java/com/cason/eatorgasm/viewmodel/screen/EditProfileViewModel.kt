package com.cason.eatorgasm.viewmodel.screen

import android.app.Activity
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.EditProfileView
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val handle: SavedStateHandle
    , private val profileUsecase: FetchMyProfileUseCaseExecutor): ViewModel(), EditProfileView.ActionListener {

    private lateinit var mActivityRef: WeakReference<Activity>
    private lateinit var editProfileView: EditProfileView

    fun setParentContext(parentContext: Activity) {
        mActivityRef = WeakReference(parentContext)
    }

    fun setView(view: EditProfileView) {
        editProfileView = view
        view.setActionListener(this)
        view.setUpdateProfileLiveData(profileUsecase.getUpdateProfileResultLiveData())
        view.setUpdateProfileImageLiveData(profileUsecase.getUpdateProfileImageResultLiveData())
    }

    fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>) {
        editProfileView.setUserProfileLiveData(liveData)
    }

    fun updateProfile(userInfo: UserInfoModel) {
        profileUsecase.updateProfileData(userInfo)
    }

    fun updateProfileImage(uri: Uri) {
        profileUsecase.updateProfileImage(uri)
    }

    fun fetchProfileImage() {
        profileUsecase.fetchProfileImage()
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