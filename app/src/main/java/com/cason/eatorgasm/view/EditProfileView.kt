package com.cason.eatorgasm.view

import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.action.OnNotifySignInSuccessAction
import com.cason.eatorgasm.view.action.OnRenderToastAction
import com.cason.eatorgasm.view.action.OnRequestedSignInAction

interface EditProfileView {

    fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>)
    fun setActionListener(actionListener: ActionListener)

    interface ActionListener : OnRenderToastAction
}