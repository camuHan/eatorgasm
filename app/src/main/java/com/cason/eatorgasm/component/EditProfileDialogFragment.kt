package com.cason.eatorgasm.component

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatValue
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.view.EditProfileViewImpl
import com.cason.eatorgasm.viewmodel.screen.EditProfileViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileDialogFragment : BaseDialogFragment() {
    private final val TAG = "EditProfileActivity"

    private lateinit var mBinding: EditProfileLayoutBinding

    val updateProfileTextWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mBinding.btnProfileUpdate.isEnabled = true
        }
        override fun afterTextChanged(p0: Editable?) {}
    }

    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mEditProfileViewModel: EditProfileViewModel by viewModels()

    private val changeProfileResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try{
                val uri = result?.data?.data
                mEditProfileViewModel.changeProfileImage(uri!!)

            }catch (e:Exception){}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setOnDismissListener {
            CMLog.e("HSH", "onDismiss")
            setFragmentResult(EatValue.RESULT_EDIT_PROFILE, Bundle())
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(true)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = EditProfileLayoutBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()

    }

    private fun initLayout() {
        initViewList()
        mHomeViewModel.loadUserData()

        mEditProfileViewModel.setView(EditProfileViewImpl(requireContext(), mBinding, viewLifecycleOwner))
        mEditProfileViewModel.setUserProfileLiveData(mHomeViewModel.getUserLiveData())

        mBinding.btnProfileUpdate.setOnClickListener {
            val data: UserInfoModel? = mBinding.profile
            if(data != null) {
                data.name = mBinding.clPrivateName.info
                data.email = mBinding.clPrivateEmail.info
                data.phoneNumber = mBinding.clPrivatePhoneNumber.info
                data.photoUrl = mBinding.clPrivatePhotoUrl.info

                mEditProfileViewModel.updateProfile(data)
            }
        }

        mBinding.ivProfileChange.setOnClickListener {
            val tempIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val intent = Intent.createChooser(tempIntent, requireContext().getString(R.string.insert_image_chooser_title));
            changeProfileResult.launch(intent)
        }

        mBinding.clPrivateName.etProfileInfo.addTextChangedListener(updateProfileTextWatcher)
        mBinding.clPrivateEmail.etProfileInfo.addTextChangedListener(updateProfileTextWatcher)
        mBinding.clPrivatePhoneNumber.etProfileInfo.addTextChangedListener(updateProfileTextWatcher)
    }

    private fun initViewList() {
        mBinding.clPrivateUid.tvProfileInfo.text = "User ID"
        mBinding.clPrivateUid.etProfileInfo.isEnabled = false
        mBinding.clPrivateName.tvProfileInfo.text = "Name"
        mBinding.clPrivateEmail.tvProfileInfo.text = "Email"
        mBinding.clPrivatePhoneNumber.tvProfileInfo.text = "PHoneNumber"
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        CMLog.e("HSH", "onDismiss")
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
        CMLog.e("HSH", "onDismiss")
    }

    override fun dismiss() {
        super.dismiss()
        CMLog.e("HSH", "onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        CMLog.e("HSH", "onCancel")
    }

    override fun onDestroy() {
        super.onDestroy()
        CMLog.e("HSH", "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CMLog.e("HSH", "onDestroyView")
        setFragmentResult(EatValue.RESULT_EDIT_PROFILE, Bundle())
    }

}