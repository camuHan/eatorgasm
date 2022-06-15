package com.cason.eatorgasm.component

import android.app.Activity
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
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.viewimpl.EditProfileViewImpl
import com.cason.eatorgasm.viewmodel.screen.EditProfileViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    private var mContentList = arrayListOf<String>()
    private var mTitleList = arrayListOf<String>()

    private var mUserProfile = UserInfoModel()

    val mUser = FirebaseAuth.getInstance().currentUser
    val mDb = Firebase.firestore

    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mEditProfileViewModel: EditProfileViewModel by viewModels()

    private val changeProfileResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try{
                val uri = result?.data?.data
                mEditProfileViewModel.chagneProfileImage(uri!!)

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

        mEditProfileViewModel.setView(EditProfileViewImpl(requireContext(), mBinding, this))
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
}