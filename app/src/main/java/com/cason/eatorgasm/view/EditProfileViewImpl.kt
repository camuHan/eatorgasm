package com.cason.eatorgasm.view

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.model.entity.UserInfoModel
import dagger.hilt.android.qualifiers.ApplicationContext

class EditProfileViewImpl(@ApplicationContext private val context: Context,
                          private val binding: EditProfileLayoutBinding,
                          private val lifecycleOwner: LifecycleOwner) : EditProfileView {

    override fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>) {
        liveData.observe(lifecycleOwner) {
            binding.profile = it
            Glide.with(context).load(it?.photoUrl).circleCrop().into(binding.ivProfileCircle)
        }
    }

    override fun setUpdateProfileLiveData(liveData: LiveData<Boolean>) {
        liveData.observe(lifecycleOwner) {
            if (it) {
                binding.btnProfileUpdate.isEnabled = false
            } else {

            }
        }
    }

    override fun setChangeProfileImageLiveData(liveData: LiveData<String>) {
        liveData.observe(lifecycleOwner) {
            if (it != null) {
                binding.clPrivatePhotoUrl.info = it
                Glide.with(context).load(it).circleCrop().into(binding.ivProfileCircle)
            }
        }
    }

    override fun setEnableUpdate() {
        binding.btnProfileUpdate.isEnabled = true
    }

    override fun setActionListener(actionListener: EditProfileView.ActionListener) {

    }
}