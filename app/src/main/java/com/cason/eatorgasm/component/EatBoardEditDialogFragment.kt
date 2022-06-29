package com.cason.eatorgasm.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.adapter.BoardEditImageListAdapter
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.BoardEditFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine.BundleKey.BOARD_ID
import com.cason.eatorgasm.define.EatDefine.Request.REQUEST_KEY
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.util.ProgressManager
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EatBoardEditDialogFragment : BaseDialogFragment(), ComponentContract {
    private lateinit var mBinding: BoardEditFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mBoardViewModel: BoardViewModel by viewModels()

    private var mBoardEditImageListAdapter: BoardEditImageListAdapter? = null

    private val addImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val clipData = it?.data?.clipData
            val clipDataSize = clipData?.itemCount
            if (clipData == null) { //이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
                val selectedImageUri = it.data?.data.toString()
                mBoardEditImageListAdapter?.addItem(selectedImageUri)

            } else {
                val list = ArrayList<String>()
                for (i in 0 until clipDataSize!!) { //선택 한 사진수만큼 반복
                    list.add(clipData.getItemAt(i).uri.toString())
                }
                mBoardEditImageListAdapter?.setItems(list)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = BoardEditFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boardId = arguments?.getString(BOARD_ID, "")
        if(boardId != null && boardId != "") {
            mBoardViewModel.getBoardDataByBoardId(boardId)
        }

        initializeBoardImageListView()
        setObservers()

        mBinding.btnBoardEditCamera.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.action = Intent.ACTION_PICK
            addImageResult.launch(intent)
        }

        mBinding.btnBoardEditConfirm.setOnClickListener {
            val data = BoardInfoModel()
            data.boardId = mBinding.board?.boardId
            data.title = mBinding.etBoardEditTitle.text.toString()
            data.contents = mBinding.etBoardEditContents.text.toString()
            data.contentsList = mBoardEditImageListAdapter?.getItems()
            mBoardViewModel.setBoardData(data)
        }
    }

    private fun initializeBoardImageListView() {
        mBoardEditImageListAdapter = BoardEditImageListAdapter(requireContext(), this)
        mBinding.rvBoardEditImageList.adapter = mBoardEditImageListAdapter
    }

    private fun setObservers() {
        mBoardViewModel.getBoardLiveData().observe(viewLifecycleOwner) { boardInfoModel ->
            mBinding.board = boardInfoModel
            mBoardEditImageListAdapter?.setItems(boardInfoModel.contentsList)
        }

        mBoardViewModel.getUpdateBoardLiveData().observe(viewLifecycleOwner) { isUpdate ->
            ProgressManager.dismissProgressCircular()
            dismiss()
            val bundle = Bundle()
            val boardInfoModel = mBinding.board
            if(boardInfoModel?.boardId != null) {
                bundle.putString(BOARD_ID, boardInfoModel.boardId)
            }
            setFragmentResult(REQUEST_KEY, bundle)
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