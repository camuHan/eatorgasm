package com.cason.eatorgasm.viewimpl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.PrivateView
import com.google.firebase.auth.FirebaseUser

class PrivateViewImpl (private val binding: PrivateFragmentBinding, private val lifecycleOwner: LifecycleOwner):
    PrivateView {
    private var mActionListener: PrivateView.ActionListener? = null

    override fun setFirebaseUserLiveData(liveData: LiveData<FirebaseUser?>) {
        liveData.observe(lifecycleOwner, { data: FirebaseUser? ->
            if (data == null) {
                mActionListener?.onRenderToast("로그아웃 되었습니다.")
                binding.profile = null
            } else {
                val profile = UserInfoModel()
                profile.setFirebaseUserData(data)
                binding.profile = profile
                mActionListener?.onNotifySignInSuccess()
            }
        })
    }

    override fun setActionListener(actionListener: PrivateView.ActionListener) {
        mActionListener = actionListener
    }


}