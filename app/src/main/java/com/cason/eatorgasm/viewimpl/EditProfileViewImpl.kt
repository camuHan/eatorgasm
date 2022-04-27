package com.cason.eatorgasm.viewimpl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.EditProfileView

class EditProfileViewImpl(private val binding: EditProfileLayoutBinding, private val lifecycleOwner: LifecycleOwner): EditProfileView {

    override fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>) {
        liveData.observe(lifecycleOwner, {
            binding.profile = it
        })
    }
    override fun setActionListener(actionListener: EditProfileView.ActionListener) {

    }
}