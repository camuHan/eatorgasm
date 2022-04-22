package com.cason.eatorgasm.view.profile

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cason.eatorgasm.R
import com.cason.eatorgasm.databinding.EditProfileActivityBinding
import com.cason.eatorgasm.databinding.ProfileInfoItemBinding
import com.cason.eatorgasm.model.EatUserProfileItem
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class EditProfileActivity: AppCompatActivity() {
    private lateinit var mBinding: EditProfileActivityBinding
    private var mProfileInfoAdapter: ProfileInfoRecyclerViewAdapter? = null

    private var mContentList = arrayListOf<String>()
    private var mTitleList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setLayout()
    }

    private fun setLayout() {
        mBinding = EditProfileActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        makeProfileList()
        mProfileInfoAdapter = ProfileInfoRecyclerViewAdapter(mTitleList, mContentList)
        mBinding.fileInfoRecyclerView.adapter = mProfileInfoAdapter
    }

    private fun makeProfileList() {
//        val test = EatUserProfileItem()
        val user = FirebaseAuth.getInstance().currentUser

        mTitleList.add("UserId")
        mContentList.add(user?.uid.toString())
        mTitleList.add("strName")
        mContentList.add(user?.displayName.toString())
        mTitleList.add("email")
        mContentList.add(user?.email.toString())
//        mTitleList.add("password")
//        mContentList.add(user?. password.toString())
        mTitleList.add("phoneNumber")
        mContentList.add(user?.phoneNumber.toString())
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
                mHolderBinding.fileInfoTitle.text = title
                mHolderBinding.fileInfoContent.text = content
            }
        }
    }



    internal class FileInfoRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTvTitleItem: TextView = itemView.findViewById(R.id.file_info_title)
        var mTvContentItem: TextView = itemView.findViewById(R.id.file_info_content)

        init {
            mTvTitleItem.isSelected = true
            mTvContentItem.isSelected = true
        }
    }
}