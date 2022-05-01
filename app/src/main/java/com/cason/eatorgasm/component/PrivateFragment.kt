package com.cason.eatorgasm.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.viewimpl.PrivateViewImpl
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import com.cason.eatorgasm.viewmodel.screen.PrivateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivateFragment : Fragment() {
    private lateinit var mBinding: PrivateFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mPrivateViewModel: PrivateViewModel by viewModels()

    private var resultMap = HashMap<String, ActivityResultLauncher<Intent>>()

    private val googleResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        mPrivateViewModel.googleResult(result)
    }

    private val editProfileResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = PrivateFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPrivateViewModel.setParentContext(requireActivity())
        mPrivateViewModel.setPrivateView(PrivateViewImpl(requireContext(), mBinding, this))
        mBinding.vm = mPrivateViewModel

        mPrivateViewModel.loadUserData()

        mBinding.btnPrivateGoogleSignIn.setOnClickListener {
            mPrivateViewModel.onRequestedSignIn(googleResultLauncher)
        }

        mBinding.clProfileContainer.setOnClickListener {
            val fragment = EditProfileDialogFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, fragment).addToBackStack(null).commit()
//            mPrivateViewModel.requestEditProfile(editProfileResultLauncher)
        }
    }
}