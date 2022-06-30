package com.cason.eatorgasm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.cason.eatorgasm.component.MapFragment
import com.cason.eatorgasm.component.EatBoardListFragment
import com.cason.eatorgasm.component.PrivateFragment
import com.cason.eatorgasm.component.contract.ComponentContract

class MainFragmentFactoryImpl(val contract: ComponentContract): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            EatBoardListFragment::class.java.name -> EatBoardListFragment(contract)
            MapFragment::class.java.name -> MapFragment(contract)
            PrivateFragment::class.java.name -> PrivateFragment(contract)
            else -> super.instantiate(classLoader, className)
        }
    }
}