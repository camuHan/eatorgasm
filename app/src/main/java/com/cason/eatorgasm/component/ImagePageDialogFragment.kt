package com.cason.eatorgasm.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.adapter.BoardContract
import com.cason.eatorgasm.adapter.BoardImageListAdapter
import com.cason.eatorgasm.adapter.BoardListAdapter
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.BoardFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePageDialogFragment : BaseDialogFragment(), BoardContract {
    private lateinit var mBinding: BoardFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mBoardViewModel: BoardViewModel by viewModels()

    private var mBoardImageListAdapter: BoardImageListAdapter? = null

    companion object {
        fun newInstance() = ImagePageDialogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = BoardFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private val addImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try{
                val uri = result?.data?.data.toString()
                mBoardImageListAdapter?.addItem(uri)
//                mEditProfileViewModel.updateProfileImage(uri!!)

            }catch (e:Exception){}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBoardImageListView()

        mBinding.btnBoardCamera.setOnClickListener {
            val tempIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val intent = Intent.createChooser(tempIntent, requireContext().getString(R.string.insert_image_chooser_title));
            addImageResult.launch(intent)
        }

        mBinding.btnBoardConfirm.setOnClickListener {
            val data = BoardInfoModel()
            data.title = mBinding.etBoardTitle.text.toString()
            data.contents = mBinding.etBoardContents.text.toString()
            mBoardViewModel.addBoardData(data)

            dismiss()
        }
    }

    private fun initializeBoardImageListView() {
        mBoardImageListAdapter = BoardImageListAdapter(requireContext(), this)
        mBinding.rvBoardImageList.adapter = mBoardImageListAdapter

//        mBoardViewModel.updateBoardDataList()
    }

    override fun onCommand(commandType: CMEnum.EatCommand, vararg args: Any?) {
        TODO("Not yet implemented")
    }

}