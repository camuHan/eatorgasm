package com.cason.eatorgasm.component

import android.app.Activity
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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.define.EatDefine
import com.cason.eatorgasm.define.EatDefine.Result.RESULT_EDIT_PROFILE
import com.cason.eatorgasm.model.entity.UserInfoModel
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
                data.name = mBinding.etPrivateName.text.toString()
//                data.email = mBinding.etPrivateEmail.text.toString()
                data.phoneNumber = mBinding.etPrivatePhoneNumber.text.toString()
//                data.photoUrl = mBinding.etPrivatePhotoUrl.text.toString()

                mEditProfileViewModel.updateProfile(data)
            }
        }

        mBinding.ivProfileChange.setOnClickListener {
            val tempIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val intent = Intent.createChooser(tempIntent, requireContext().getString(R.string.insert_image_chooser_title));
            changeProfileResult.launch(intent)
        }

        mBinding.etPrivateName.addTextChangedListener(updateProfileTextWatcher)
        mBinding.etPrivateEmail.addTextChangedListener(updateProfileTextWatcher)
        mBinding.etPrivatePhoneNumber.addTextChangedListener(updateProfileTextWatcher)
    }

    private fun initViewList() {

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        setFragmentResult(RESULT_EDIT_PROFILE, Bundle())
    }

}