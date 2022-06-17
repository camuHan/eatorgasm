package com.cason.eatorgasm.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.adapter.BoardListAdapter
import com.cason.eatorgasm.databinding.BoardListFragmentBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EatBoardListFragment(contract: ComponentContract) : Fragment(), ComponentContract {
    private lateinit var mBinding: BoardListFragmentBinding

    private val mBoardViewModel: BoardViewModel by viewModels()
    private var mBoardListAdapter: BoardListAdapter? = null

//    companion object {
//        fun newInstance() = EatBoardListFragment(this)
//    }

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
            val dialog = EatBoardDialogFragment()
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_EatOrgasm)
            dialog.show(parentFragmentManager, "tag");

//            val fragment = EatBoardDialogFragment()
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            transaction.add(android.R.id.content, fragment).addToBackStack(null).commit()
        }
    }

    private fun initializeBoardListView() {
        mBoardListAdapter = BoardListAdapter(requireContext(), this)
        mBinding.rvBoardList.adapter = mBoardListAdapter

        mBoardViewModel.updateBoardDataList()
    }

    private fun setObservers() {
        mBoardViewModel.getBoardLiveData().observe(viewLifecycleOwner) {
            mBoardListAdapter?.submitList(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCommand(commandType: CMEnum.EatCommand, vararg args: Any?) {
        TODO("Not yet implemented")
    }

}