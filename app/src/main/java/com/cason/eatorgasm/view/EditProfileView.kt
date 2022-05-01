package com.cason.eatorgasm.view

import android.net.Uri
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.action.OnNotifySignInSuccessAction
import com.cason.eatorgasm.view.action.OnRenderToastAction
import com.cason.eatorgasm.view.action.OnRequestedSignInAction

interface EditProfileView {

    fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>)
    fun setUpdateProfileLiveData(liveData: LiveData<Boolean>)
    fun setUpdateProfileImageLiveData(liveData: LiveData<Uri>)
    fun setActionListener(actionListener: ActionListener)

    interface ActionListener : OnRenderToastAction
}