package com.cason.eatorgasm.view

import android.net.Uri
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.view.action.OnNotifySignInSuccessAction
import com.cason.eatorgasm.view.action.OnRenderToastAction
import com.cason.eatorgasm.view.action.OnRequestedSignInAction
import com.google.firebase.auth.FirebaseUser

interface PrivateView {

    fun setFirebaseUserLiveData(liveData: LiveData<FirebaseUser?>)
    fun setUpdateProfileImageLiveData(liveData: LiveData<Uri>)
    fun setActionListener(actionListener: ActionListener)

    interface ActionListener : OnNotifySignInSuccessAction, OnRequestedSignInAction,
        OnRenderToastAction

}