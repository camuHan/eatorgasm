package com.cason.eatorgasm.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.view.action.OnNotifySignInSuccessAction
import com.cason.eatorgasm.view.action.OnRenderToastAction
import com.cason.eatorgasm.view.action.OnRequestedSignInAction
import com.cason.eatorgasm.viewimpl.PrivateViewImpl
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

interface PrivateView {

    fun setFirebaseUserLiveData(liveData: LiveData<FirebaseUser?>)
    fun setActionListener(actionListener: ActionListener)

    interface ActionListener : OnNotifySignInSuccessAction, OnRequestedSignInAction,
        OnRenderToastAction

}