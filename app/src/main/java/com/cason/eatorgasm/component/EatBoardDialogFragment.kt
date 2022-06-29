package com.cason.eatorgasm.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.cason.eatorgasm.R
import com.cason.eatorgasm.adapter.BoardImageListAdapter
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.BoardFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.ProgressManager
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

    private val mEditBoardListener = FragmentResultListener { key, bundle ->
        if (key == EatDefine.Request.REQUEST_KEY) {
            getBoardData(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = BoardFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTransparentActionBar()
        initBoardLayout()
        setObservers()

        getBoardData(arguments)
    }

    private fun initTransparentActionBar() {
        mBinding.boardToolbarLayout.background.alpha = 0
        mBinding.boardScrollview.setOnScrollChangeListener { view, x, y, oldx, oldy ->
            if(y in 0..1299) {
                val ratio = y / (1300f)
                CMLog.e("HSH", "test = $ratio")
                mBinding.boardToolbarLayout.background.alpha = (ratio * 255).toInt()
            }
        }
    }

    private fun initBoardLayout() {
        mBoardImageListAdapter = BoardImageListAdapter(requireContext(), this)

        mBinding.boardViewPager.adapter = mBoardImageListAdapter
        mBinding.boardViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.boardViewPager.offscreenPageLimit = 3

        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))
        transform.addTransformer(ViewPager2.PageTransformer{ view: View, fl: Float ->
            val v = 1-Math.abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        })
        mBinding.boardViewPager.setPageTransformer(transform)

        TabLayoutMediator(mBinding.boardTabLayout, mBinding.boardViewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        mBinding.ibBoardListMore.setOnClickListener { view ->
            val boardInfoModel = mBinding.board as BoardInfoModel
            setPopupMenu(boardInfoModel, view)
        }
    }

    private fun getBoardData(bundle: Bundle?) {
        val boardId = bundle?.getString(EatDefine.BundleKey.BOARD_ID, "")
        if(boardId != null && boardId != "") {
            mBoardViewModel.getBoardDataByBoardId(boardId)
            ProgressManager.showProgress()
        }
    }

    private fun setObservers() {
        mBoardViewModel.getBoardLiveData().observe(viewLifecycleOwner) { boardInfoModel ->
            mBinding.board = boardInfoModel
            mBinding.clBoardProfile.tvUserName.text = boardInfoModel?.name
            Glide.with(requireContext()).load(boardInfoModel?.photoUrl).circleCrop().into(mBinding.clBoardProfile.ivUserThumb)
            mBoardImageListAdapter?.setItems(boardInfoModel.contentsList)
            ProgressManager.dismissProgress()
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

    private fun setPopupMenu(item: BoardInfoModel, view: View) {
        val popupMenu = PopupMenu(context, view)
        activity?.menuInflater?.inflate(R.menu.board_more_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.board_modification -> {
                    val bundle = Bundle()
                    bundle.putString(EatDefine.BundleKey.BOARD_ID, item.boardId)
                    goEditBoard(bundle)
                    true
                }
                R.id.board_delete -> {
                    mBoardViewModel.deleteBoardDataByBoardId(item.boardId)
                    true
                }
                else -> {
                    false
                }
            }
        }
        popupMenu.show()
    }

    private fun goEditBoard(bundle: Bundle) {
        val dialog = EatBoardEditDialogFragment()
        dialog.setStyle(STYLE_NORMAL, R.style.Theme_EatOrgasm)
        dialog.arguments = bundle
        parentFragmentManager.setFragmentResultListener(EatDefine.Request.REQUEST_KEY, this, mEditBoardListener)
        dialog.show(parentFragmentManager, "board");
    }

}