package com.cason.eatorgasm.view.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cason.eatorgasm.databinding.EditProfileActivityBinding
import com.cason.eatorgasm.databinding.ProfileInfoItemBinding
import com.cason.eatorgasm.model.EatUserProfileItem
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.ToastManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList


class EditProfileActivity: AppCompatActivity(), View.OnClickListener {
    private final val TAG = "EditProfileActivity"

    private lateinit var mBinding: EditProfileActivityBinding
    private var mProfileInfoAdapter: ProfileInfoRecyclerViewAdapter? = null

    private var mContentList = arrayListOf<String>()
    private var mTitleList = arrayListOf<String>()

    private var mUserProfile = EatUserProfileItem()

    val mUser = FirebaseAuth.getInstance().currentUser
    val mDb = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayout()
    }

    private fun setLayout() {
        mBinding = EditProfileActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViewList()
//        updateProfileInfo()

//        mProfileInfoAdapter = ProfileInfoRecyclerViewAdapter(mTitleList, mContentList)
//        mBinding.rvProfileInfo.adapter = mProfileInfoAdapter
//
//        mBinding.rvProfileInfo.adapter?.notifyDataSetChanged()

        mBinding.btnProfileUpdate.setOnClickListener {
            updateProfileInfo()
            updateProfile()
        }

        mBinding.clPrivateUid.ibProfileInfoEdit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

    }

    private fun initViewList() {
        mDb.collection("users").document(mUser?.uid.toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    mUserProfile.userId = data?.get("userId").toString()
                    mUserProfile.strName = data?.get("strName").toString()
                    mUserProfile.email = data?.get("email").toString()
                    mUserProfile.phoneNumber = data?.get("phoneNumber").toString()
                    CMLog.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    mUserProfile.userId = mUser?.uid
                    mUserProfile.strName = mUser?.displayName
                    mUserProfile.email = mUser?.email
                    mUserProfile.phoneNumber = mUser?.phoneNumber
                    CMLog.d(TAG, "No such document")
                }
                mBinding.profile = mUserProfile
            }
            .addOnFailureListener { exception ->
                CMLog.d(TAG, "get failed with \n$exception")
            }

        mBinding.clPrivateUid.tvProfileInfo.text = "User ID"
//        mBinding.clPrivateUid.etProfileInfo.setText(mUserProfile.userId)
        mBinding.clPrivateUid.etProfileInfo.isEnabled = false
        mBinding.clPrivateName.tvProfileInfo.text = "Name"
//        mBinding.clPrivateName.etProfileInfo.setText(mUserProfile.strName)
//        mBinding.clPrivateName.etProfileInfo.isEnabled = false
        mBinding.clPrivateEmail.tvProfileInfo.text = "Email"
//        mBinding.clPrivateEmail.etProfileInfo.setText(mUserProfile.email)
//        mBinding.clPrivateEmail.etProfileInfo.isEnabled = false
        mBinding.clPrivatePhoneNumber.tvProfileInfo.text = "PHoneNumber"
//        mBinding.clPrivatePhoneNumber.etProfileInfo.setText(mUserProfile.phoneNumber)
//        mBinding.clPrivatePhoneNumber.etProfileInfo.isEnabled = false
    }

    private fun updateProfileInfo() {
        mUserProfile.userId = mBinding.clPrivateUid.etProfileInfo.text.toString()
        mUserProfile.strName = mBinding.clPrivateName.etProfileInfo.text.toString()
        mUserProfile.email = mBinding.clPrivateEmail.etProfileInfo.text.toString()
        mUserProfile.phoneNumber = mBinding.clPrivatePhoneNumber.etProfileInfo.text.toString()
    }

    private fun updateProfile() {
        mDb.collection("users").document(mUser?.uid.toString()).set(mUserProfile)
            .addOnSuccessListener {
                CMLog.d(TAG, "DocumentSnapshot successfully written!")
                ToastManager.INSTANCE.onMessage(this, "업데이트 되었습니다.")
            }
            .addOnFailureListener {
                    e -> CMLog.w(TAG, "Error writing document \n$e")
                ToastManager.INSTANCE.onMessage(this, "업데이트에 실패하였습니다.")
            }
    }

    private class ProfileInfoRecyclerViewAdapter(private val mTitleItems: ArrayList<String>, private val mContentItems: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var mContext: Context? = null
//        var mItems = ArrayList<EatUserProfileItem>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = ProfileInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProfileInfoRecyclerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder: ProfileInfoRecyclerViewHolder = holder as ProfileInfoRecyclerViewHolder
            viewHolder.bind(mTitleItems[position], mContentItems[position])
        }

        override fun getItemCount(): Int {
            return mTitleItems.size
        }

        inner class ProfileInfoRecyclerViewHolder(binding: ProfileInfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
            private val mHolderBinding = binding

            fun bind(title: String, content: String) {
                mHolderBinding.tvProfileInfoTitle.text = title
                mHolderBinding.tvProfileInfoContent.text = content
            }
        }
    }
}