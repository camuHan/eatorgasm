package com.cason.eatorgasm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cason.eatorgasm.databinding.BoardListItemBinding
import com.cason.eatorgasm.model.entity.BoardInfoModel

class BoardListAdapter(private var context: Context, contract: BoardContract) :
    ListAdapter<BoardInfoModel, RecyclerView.ViewHolder>(HomeFileItemDiffCallback()) {

    var mBoardContract = contract

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BoardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: BoardListViewHolder = holder as BoardListViewHolder
        viewHolder.bind(getItem(position))
    }

    inner class BoardListViewHolder(binding: BoardListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {
        private val mHolderBinding = binding

        fun bind(item: BoardInfoModel?) {
            mHolderBinding.model = item
            mHolderBinding.clPrivateProfile.tvUserName.text = item?.name
            Glide.with(context).load(item?.photoUrl).circleCrop().into(mHolderBinding.clPrivateProfile.ivUserThumb)
        }

        private fun onSingleClick(view: View?) {
//            if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
//                return
//            }
//
//            val homeFileItem = getItem(bindingAdapterPosition)
//            when (view?.id) {
//                mHolderBinding.root.id -> {
//                    if (homeFileItem != null) {
//                        mHomeContract.onCommand(HomeEnum.HomeCommand.CLICKED, homeFileItem, bindingAdapterPosition)
//                    }
//                }
//                mHolderBinding.homeBrowserFileMore.id -> {
//                    if (homeFileItem != null) {
//                        mHomeContract.onCommand(HomeEnum.HomeCommand.BUTTON_CLICKED, homeFileItem, bindingAdapterPosition)
//                    }
//                }
//            }
        }

        override fun onLongClick(v: View?): Boolean {
//            val homeFileItem = getItem(bindingAdapterPosition)
//            when (v?.id) {
//                mHolderBinding.root.id -> {
//                    if (homeFileItem != null) {
//                        mHomeContract.onCommand(HomeEnum.HomeCommand.LONG_CLICKED, homeFileItem)
//                    }
//                }
//            }
            return true
        }

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//            mHomeContract.onCommand(HomeEnum.HomeCommand.CHECKED, bindingAdapterPosition, isChecked)
        }
    }
}

class HomeFileItemDiffCallback : DiffUtil.ItemCallback<BoardInfoModel>() {
    override fun areItemsTheSame(
        oldItem: BoardInfoModel,
        newItem: BoardInfoModel
    ): Boolean {
        return oldItem.userId + oldItem.title == newItem.userId + newItem.title
    }

    override fun areContentsTheSame(
        oldItem: BoardInfoModel,
        newItem: BoardInfoModel
    ): Boolean {
        return oldItem == newItem
    }

}