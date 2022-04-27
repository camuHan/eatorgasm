package com.cason.eatorgasm.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.EditProfileLayoutBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.ToastManager
import com.cason.eatorgasm.view.EditProfileView
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
//    private var mProfileInfoAdapter: EditProfileActivity.ProfileInfoRecyclerViewAdapter? = null

    private var mContentList = arrayListOf<String>()
    private var mTitleList = arrayListOf<String>()

    private var mUserProfile = UserInfoModel()

    val mUser = FirebaseAuth.getInstance().currentUser
    val mDb = Firebase.firestore

    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mEditProfileViewModel: EditProfileViewModel by viewModels()

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

        mEditProfileViewModel.setView(EditProfileViewImpl(mBinding, this))
        mEditProfileViewModel.setUserProfileLiveData(mHomeViewModel.getUserLiveData())

        mBinding.btnProfileUpdate.setOnClickListener {
//            updateProfileInfo()
//            updateProfile()
        }

        mHomeViewModel.loadUserData()

//        mBinding.clPrivateUid.ibProfileInfoEdit.setOnClickListener(this)
    }

    private fun initViewList() {
        mBinding.clPrivateUid.tvProfileInfo.text = "User ID"
        mBinding.clPrivateUid.etProfileInfo.isEnabled = false
        mBinding.clPrivateName.tvProfileInfo.text = "Name"
        mBinding.clPrivateEmail.tvProfileInfo.text = "Email"
        mBinding.clPrivatePhoneNumber.tvProfileInfo.text = "PHoneNumber"
    }

    private fun updateProfileInfo() {
        mUserProfile.userId = mBinding.clPrivateUid.etProfileInfo.text.toString()
        mUserProfile.name = mBinding.clPrivateName.etProfileInfo.text.toString()
        mUserProfile.email = mBinding.clPrivateEmail.etProfileInfo.text.toString()
        mUserProfile.phoneNumber = mBinding.clPrivatePhoneNumber.etProfileInfo.text.toString()
    }

    private fun updateProfile() {
        mDb.collection("users").document(mUser?.uid.toString()).set(mUserProfile)
            .addOnSuccessListener {
                CMLog.d(TAG, "DocumentSnapshot successfully written!")
                ToastManager.INSTANCE.onMessage(context, "업데이트 되었습니다.")
            }
            .addOnFailureListener {
                    e -> CMLog.w(TAG, "Error writing document \n$e")
                ToastManager.INSTANCE.onMessage(context, "업데이트에 실패하였습니다.")
            }
    }
}