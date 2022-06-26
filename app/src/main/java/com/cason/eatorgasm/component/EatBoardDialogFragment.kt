package com.cason.eatorgasm.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.adapter.BoardEditImageListAdapter
import com.cason.eatorgasm.adapter.BoardImageListAdapter
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.BoardFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EatBoardDialogFragment : BaseDialogFragment(), ComponentContract {
    private lateinit var mBinding: BoardFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mBoardViewModel: BoardViewModel by viewModels()

    private var mBoardImageListAdapter: BoardImageListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = BoardFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBoardImageListAdapter = BoardImageListAdapter(requireContext(), this)

        mBinding.boardViewPager.adapter = mBoardImageListAdapter
        mBinding.boardViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(mBinding.boardTabLayout, mBinding.boardViewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        val boardId = arguments?.getString(EatDefine.BundleKey.BOARD_ID, "")
        if(boardId != null && boardId != "") {
            mBoardViewModel.getBoardDataByBoardId(boardId)
        }

        initializeBoardImageListView()
        setObservers()
    }

    private fun initializeBoardImageListView() {

    }

    private fun setObservers() {
        mBoardViewModel.getBoardLiveData().observe(viewLifecycleOwner) { boardInfoModel ->
            mBinding.board = boardInfoModel
            mBinding.clBoardProfile.tvUserName.text = boardInfoModel?.name
            Glide.with(requireContext()).load(boardInfoModel?.photoUrl).circleCrop().into(mBinding.clBoardProfile.ivUserThumb)
            mBoardImageListAdapter?.setItems(boardInfoModel.contentsList)
        }
    }

    override fun onCommand(commandType: CMEnum.EatCommand, vararg args: Any?) {
        when(commandType) {
            CMEnum.EatCommand.IMAGE_CLICKED -> {
//                Glide.with(requireContext()).load(args[0].toString()).into(mBinding.ivCustomImage)
//                mBinding.ivCustomImage.visibility = VISIBLE
            }
            else -> {}
        }
    }

}