package com.cason.eatorgasm.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.adapter.BoardListAdapter
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.BoardListFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine
import com.cason.eatorgasm.define.EatDefine.BundleKey.BOARD_ID
import com.cason.eatorgasm.define.EatDefine.BundleKey.BOARD_INFO_MODEL
import com.cason.eatorgasm.define.EatDefine.TransitionName.IMAGE_TRANSITION
import com.cason.eatorgasm.define.EatDefine.TransitionName.PROFILE_TRANSITION
import com.cason.eatorgasm.define.EatDefine.TransitionName.TITLE_TRANSITION
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

    private fun goBoard(board: BoardInfoModel, imageView: ImageView, profileView: ConstraintLayout, titleView: TextView) {
        val intent = Intent(activity, EatBoardActivity::class.java)
        intent.putExtra(BOARD_INFO_MODEL, board)
        val imagePair: Pair<View?,String?> = Pair(imageView, IMAGE_TRANSITION)
        val profilePair: Pair<View?,String?> = Pair(profileView, PROFILE_TRANSITION)
        val titlePair: Pair<View?,String?> = Pair(titleView, TITLE_TRANSITION)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(), imagePair, profilePair, titlePair)

        boardResultLauncher.launch(intent, options)

    }

    private val boardResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            updateBoardDataListCommand()
        }
    }

    private fun goEditBoard(bundle: Bundle) {
        val dialog = EatBoardEditDialogFragment()
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_EatOrgasm)
        dialog.arguments = bundle
        parentFragmentManager.setFragmentResultListener(EatDefine.Request.REQUEST_KEY, this, mEditBoardListener)
        dialog.show(parentFragmentManager, "board");
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
                val imageView = args[1] as ImageView
                val prfileView = args[2] as ConstraintLayout
                val titleView = args[3] as TextView

                goBoard(item, imageView, prfileView, titleView)
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