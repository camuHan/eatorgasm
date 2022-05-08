package com.cason.eatorgasm.viewimpl

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.cason.eatorgasm.R
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.view.EditProfileView
import dagger.hilt.android.qualifiers.ApplicationContext

class EditProfileViewImpl(@ApplicationContext private val context: Context, private val binding: EditProfileLayoutBinding, private val lifecycleOwner: LifecycleOwner): EditProfileView {

    override fun setUserProfileLiveData(liveData: LiveData<UserInfoModel?>) {
        liveData.observe(lifecycleOwner) {
            binding.profile = it
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

    override fun setUpdateProfileImageLiveData(liveData: LiveData<Uri>) {
        liveData.observe(lifecycleOwner) {
            if (it != null) {
                Glide.with(context).load(it).circleCrop().into(binding.ivProfileCircle)
            }
        }
    }

    override fun setActionListener(actionListener: EditProfileView.ActionListener) {

    }
}