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
import com.cason.eatorgasm.databinding.BoardListItemBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine.TransitionName.IMAGE_TRANSITION
import com.cason.eatorgasm.model.entity.BoardInfoModel

class BoardListAdapter(private var context: Context, contract: ComponentContract) :
    ListAdapter<BoardInfoModel, RecyclerView.ViewHolder>(BoardItemDiffCallback()) {

    var mBoardContract = contract
    lateinit var mBinding: BoardListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding = BoardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mBinding.ivBoardListImage.transitionName = IMAGE_TRANSITION
        return BoardListViewHolder(mBinding)
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

            if(item?.contentsList?.size != 0) {
                Glide.with(context).load(item?.contentsList?.get(0)).centerCrop()
                    .into(mHolderBinding.ivBoardListImage)
            }

            mHolderBinding.root.setOnClickListener {
                mBoardContract.onCommand(CMEnum.EatCommand.BOARD_ITEM_CLICKED, getItem(adapterPosition), mHolderBinding.ivBoardListImage, mHolderBinding.clPrivateProfile.clPrivateProfile)
            }

            mHolderBinding.ibBoardListMore.setOnClickListener { view ->
                mBoardContract.onCommand(CMEnum.EatCommand.BOARD_MORE_MENU_CLICKED, getItem(adapterPosition), view)
            }
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

class BoardItemDiffCallback : DiffUtil.ItemCallback<BoardInfoModel>() {
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