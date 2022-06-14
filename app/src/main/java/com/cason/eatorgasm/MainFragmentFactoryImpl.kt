package com.cason.eatorgasm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.cason.eatorgasm.component.MapFragment
import com.cason.eatorgasm.component.BoardListFragment
import com.cason.eatorgasm.component.PrivateFragment

class MainFragmentFactoryImpl(): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            BoardListFragment::class.java.name -> BoardListFragment()
            MapFragment::class.java.name -> MapFragment()
            PrivateFragment::class.java.name -> PrivateFragment()
//            HomeFileTypeFragment::class.java.name -> HomeFileTypeFragment(contract)
//            HomeFavoriteFragment::class.java.name -> HomeFavoriteFragment(contract)
//            WordFileTypeTabFragment::class.java.name -> WordFileTypeTabFragment(contract)
//            SheetFileTypeTabFragment::class.java.name -> SheetFileTypeTabFragment(contract)
//            SlideFileTypeTabFragment::class.java.name -> SlideFileTypeTabFragment(contract)
//            PdfFileTypeTabFragment::class.java.name -> PdfFileTypeTabFragment(contract)
//            TextFileTypeTabFragment::class.java.name -> TextFileTypeTabFragment(contract)
//            HwpFileTypeTabFragment::class.java.name -> HwpFileTypeTabFragment(contract)
            else -> super.instantiate(classLoader, className)
        }
    }
}