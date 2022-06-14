package com.cason.eatorgasm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cason.eatorgasm.databinding.BoardImageListItemBinding
import com.cason.eatorgasm.define.CMEnum

class BoardImageListAdapter(private var context: Context, contract: BoardContract) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mBoardContract = contract
    var mItems = ArrayList<String>()

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

    fun setItems(items: ArrayList<String>) {
        mItems = items
        notifyDataSetChanged()
    }

    inner class BoardImageListViewHolder(binding: BoardImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mHolderBinding = binding

        fun bind(item: String) {
            Glide.with(context).load(item).centerCrop().into(mHolderBinding.ivBoardImageListImage)
            mHolderBinding.ivBoardImageListCancel.setOnClickListener {
                mItems.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
            mHolderBinding.ivBoardImageListImage.setOnClickListener {
//                Glide.with(context).
                mBoardContract.onCommand(CMEnum.EatCommand.IMAGE_CLICKED, mItems[adapterPosition])

            }
        }
    }
}