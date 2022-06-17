package com.cason.eatorgasm.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.MapFragmentBinding
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel

class MapFragment(contract: ComponentContract) : Fragment() {
    private lateinit var mBinding: MapFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
//    private val mBoardViewModel: BoardViewModel by viewModels()

//    companion object {
//        fun newInstance() = MapFragment()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = MapFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}