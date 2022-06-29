package com.cason.eatorgasm.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.adapter.BoardListAdapter
import com.cason.eatorgasm.databinding.BoardListFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine
import com.cason.eatorgasm.define.EatDefine.BundleKey.BOARD_ID
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.util.ProgressManager
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EatBoardListFragment(contract: ComponentContract) : Fragment(), ComponentContract {
    private lateinit var mBinding: BoardListFragmentBinding

    private val mBoardViewModel: BoardViewModel by viewModels()
    private var mBoardListAdapter: BoardListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = BoardListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBoardListView()
        setObservers()

        mBinding.fbNewBoardAdd.setOnClickListener {
            goEditBoard(Bundle())
        }

        updateBoardDataListCommand()
    }

    private fun initializeBoardListView() {
        mBoardListAdapter = BoardListAdapter(requireContext(), this)
        mBinding.rvBoardList.adapter = mBoardListAdapter
    }

    private fun setObservers() {
        mBoardViewModel.getBoardListLiveData().observe(viewLifecycleOwner) {
            mBoardListAdapter?.submitList(it)
            ProgressManager.dismissProgress()
        }
    }

    private fun setPopupMenu(item: BoardInfoModel, view: View) {
        val popupMenu = PopupMenu(context, view)
        activity?.menuInflater?.inflate(R.menu.board_more_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.board_modification -> {
                    val bundle = Bundle()
                    bundle.putString(BOARD_ID, item.boardId)
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

    private fun goBoard(bundle: Bundle) {
        val dialog = EatBoardDialogFragment()
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyAnimationTheme)
        dialog.arguments = bundle
        parentFragmentManager.setFragmentResultListener(EatDefine.Request.REQUEST_KEY, this, mBoardListener)
        dialog.show(parentFragmentManager, "board");
    }

    private fun goEditBoard(bundle: Bundle) {
        val dialog = EatBoardEditDialogFragment()
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_EatOrgasm)
        dialog.arguments = bundle
        parentFragmentManager.setFragmentResultListener(EatDefine.Request.REQUEST_KEY, this, mEditBoardListener)
        dialog.show(parentFragmentManager, "board");
    }

    private val mBoardListener = FragmentResultListener { key, bundle ->
        if (key == EatDefine.Request.REQUEST_KEY) {
            updateBoardDataListCommand()
        }
    }

    private val mEditBoardListener = FragmentResultListener { key, bundle ->
        if (key == EatDefine.Request.REQUEST_KEY) {
            updateBoardDataListCommand()
        }
    }

    override fun onCommand(commandType: CMEnum.EatCommand, vararg args: Any?) {
        when(commandType) {
            CMEnum.EatCommand.BOARD_ITEM_CLICKED -> {
                val item = args[0] as BoardInfoModel

                val bundle = Bundle()
                bundle.putString(BOARD_ID, item.boardId)
                goBoard(bundle)
            }
            CMEnum.EatCommand.BOARD_MORE_MENU_CLICKED -> {
                setPopupMenu(args[0] as BoardInfoModel, args[1] as View)
            }
        }
    }

    private fun updateBoardDataListCommand() {
        ProgressManager.showProgress()
        mBoardViewModel.updateBoardDataList()
    }
}