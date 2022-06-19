package com.cason.eatorgasm.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.adapter.BoardImageListAdapter
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.BoardFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatValue.BOARD_ID
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EatBoardDialogFragment : BaseDialogFragment(), ComponentContract {
    private lateinit var mBinding: BoardFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mBoardViewModel: BoardViewModel by viewModels()

    private var mBoardImageListAdapter: BoardImageListAdapter? = null

    private val addImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val clipData = it?.data?.clipData
            val clipDataSize = clipData?.itemCount
            if (clipData == null) { //이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
                val selectedImageUri = it.data?.data.toString()
                mBoardImageListAdapter?.addItem(selectedImageUri)

            } else {
                val list = ArrayList<String>()
                for (i in 0 until clipDataSize!!) { //선택 한 사진수만큼 반복
                    list.add(clipData.getItemAt(i).uri.toString())
                }
                mBoardImageListAdapter?.setItems(list)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = BoardFragmentBinding.inflate(inflater, container, false)
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

        mBinding.btnBoardCamera.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.action = Intent.ACTION_PICK
            addImageResult.launch(intent)
        }

        mBinding.btnBoardConfirm.setOnClickListener {
            val data = BoardInfoModel()
            data.boardId = mBinding.board?.boardId
            data.title = mBinding.etBoardTitle.text.toString()
            data.contents = mBinding.etBoardContents.text.toString()
            data.contentsList = mBoardImageListAdapter?.getItems()
            mBoardViewModel.addBoardData(data)
            dismiss()
        }
    }

    private fun initializeBoardImageListView() {
        mBoardImageListAdapter = BoardImageListAdapter(requireContext(), this)
        mBinding.rvBoardImageList.adapter = mBoardImageListAdapter

//        mBoardViewModel.updateBoardDataList()
    }

    private fun setObservers() {
        mBoardViewModel.getBoardLiveData().observe(viewLifecycleOwner) { boardInfoModel ->
            mBinding.board = boardInfoModel
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