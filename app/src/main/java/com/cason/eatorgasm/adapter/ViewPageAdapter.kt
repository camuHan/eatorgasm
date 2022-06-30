package com.cason.eatorgasm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class ViewPageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){
    private var fragments : ArrayList<Fragment> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int = fragments.size

    fun addItems(fragment: Fragment){
        fragments.add(fragment)
    }

    fun getItem(index: Int): Fragment {
        return fragments.get(index)
    }
}