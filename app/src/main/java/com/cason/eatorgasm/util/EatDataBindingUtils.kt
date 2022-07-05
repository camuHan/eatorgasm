package com.cason.eatorgasm.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cason.eatorgasm.adapter.BoardEditImageListAdapter

object EatDataBindingUtils {

    @BindingAdapter("bind:item")
    @JvmStatic
    fun bindItem(recyclerView: RecyclerView, contentsList: ArrayList<String>?) {
        (recyclerView.adapter as BoardEditImageListAdapter).setItems(contentsList)
    }

}