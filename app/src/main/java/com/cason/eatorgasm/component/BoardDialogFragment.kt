package com.cason.eatorgasm.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.component.base.BaseDialogFragment
import com.cason.eatorgasm.databinding.BoardFragmentBinding
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardDialogFragment : BaseDialogFragment() {
    private lateinit var mBinding: BoardFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mBoardViewModel: BoardViewModel by viewModels()

    companion object {
        fun newInstance() = BoardDialogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = BoardFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnBoardConfirm.setOnClickListener {
            val data = BoardInfoModel()

            data.title = mBinding.etBoardTitle.text.toString()
            data.contents = mBinding.etBoardContents.text.toString()
            mBoardViewModel.addBoardData(data)

            dismiss()
        }
    }

}