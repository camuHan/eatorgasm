package com.cason.eatorgasm.viewimpl

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.cason.eatorgasm.R
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.PrivateView
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.qualifiers.ApplicationContext

class PrivateViewImpl (@ApplicationContext private val context: Context, private val binding: PrivateFragmentBinding, private val lifecycleOwner: LifecycleOwner):
    PrivateView {
    private var mActionListener: PrivateView.ActionListener? = null

    override fun setFirebaseUserLiveData(liveData: LiveData<FirebaseUser?>) {
        liveData.observe(lifecycleOwner, { data: FirebaseUser? ->
            if (data == null) {
                mActionListener?.onRenderToast("로그아웃 되었습니다.")
                binding.profile = null
                Glide.with(context).load(R.drawable.ic_eat_account_profile_circle).circleCrop().into(binding.ivUserThumb)
            } else {
                val profile = UserInfoModel()
                profile.setFirebaseUserData(data)
                binding.profile = profile
                mActionListener?.onNotifySignInSuccess()
                Glide.with(context).load(R.drawable.splash_eat_cason).circleCrop().into(binding.ivUserThumb)
            }
        })
    }

    override fun setActionListener(actionListener: PrivateView.ActionListener) {
        mActionListener = actionListener
    }


}