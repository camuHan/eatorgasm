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
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.CommentListItemBinding
import com.cason.eatorgasm.model.entity.CommentInfoModel

class BoardCommentsAdapter(private var context: Context, contract: ComponentContract) :
    ListAdapter<CommentInfoModel, RecyclerView.ViewHolder>(CommentItemDiffCallback()) {

    var mBoardContract = contract
    lateinit var mBinding: CommentListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding = CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentListViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: CommentListViewHolder = holder as CommentListViewHolder
        viewHolder.bind(getItem(position))
    }

    inner class CommentListViewHolder(binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {
        private val mHolderBinding = binding

        fun bind(item: CommentInfoModel?) {
            mHolderBinding.comment = item
            Glide.with(context).load(item?.photoUrl).circleCrop().into(mHolderBinding.ivCommentUserThumb)

//            if(item?.contentsList?.size != 0) {
//                Glide.with(context).load(item?.contentsList?.get(0)).centerCrop()
//                    .into(mHolderBinding.ivBoardListImage)
//            }

            mHolderBinding.root.setOnClickListener {

            }

//            mHolderBinding.ibBoardListMore.setOnClickListener { view ->
//                mBoardContract.onCommand(CMEnum.EatCommand.BOARD_MORE_MENU_CLICKED, getItem(adapterPosition), view)
//            }
            mHolderBinding.executePendingBindings()
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

class CommentItemDiffCallback : DiffUtil.ItemCallback<CommentInfoModel>() {
    override fun areItemsTheSame(
        oldItem: CommentInfoModel,
        newItem: CommentInfoModel
    ): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(
        oldItem: CommentInfoModel,
        newItem: CommentInfoModel
    ): Boolean {
        return oldItem == newItem
    }

}