package com.cason.eatorgasm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.BoardImageListItemBinding
import com.cason.eatorgasm.define.EatDefine.TransitionName.IMAGE_TRANSITION

class BoardImageListAdapter(private var context: Context, contract: ComponentContract) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mBoardContract = contract
    private var mItems = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BoardImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardImageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as BoardImageListViewHolder
        viewHolder.bind(mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addItem(item: String) {
        mItems.add(item)
        notifyItemInserted(mItems.size)
    }

    fun setItems(items: ArrayList<String>?) {
        if(items == null) {
            return
        }

        mItems = items
        notifyItemRangeChanged(0, itemCount)
    }

    fun getItems(): ArrayList<String> {
        return this.mItems
    }

    inner class BoardImageListViewHolder(binding: BoardImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mHolderBinding = binding

        fun bind(item: String) {
            if(adapterPosition == 0) {
                mHolderBinding.ivBoardImageListImage.transitionName = IMAGE_TRANSITION
            }
            Glide.with(context).load(item).centerCrop().into(mHolderBinding.ivBoardImageListImage)
            mHolderBinding.ivBoardImageListImage.setOnClickListener {
//                mBoardContract.onCommand(CMEnum.EatCommand.IMAGE_CLICKED, mItems[adapterPosition])
            }
        }
    }
}