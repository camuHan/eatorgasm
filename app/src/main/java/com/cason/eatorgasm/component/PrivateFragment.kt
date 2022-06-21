package com.cason.eatorgasm.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.*
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.define.EatDefine.Result.RESULT_EDIT_PROFILE
import com.cason.eatorgasm.view.PrivateViewImpl
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import com.cason.eatorgasm.viewmodel.screen.PrivateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivateFragment(contract: ComponentContract) : Fragment() {
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

        mBinding.btnPrivateGoogleSignIn.setOnClickListener {
            mPrivateViewModel.onRequestedSignIn(googleResultLauncher)
        }

        mBinding.clProfileContainer.setOnClickListener {
            val dialog = EditProfileDialogFragment()
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_EatOrgasm)
            dialog.show(parentFragmentManager, "tag");

//            val fragment = EditProfileDialogFragment()
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            parentFragmentManager.setFragmentResultListener(RESULT_EDIT_PROFILE, this, mEditProfileListener)
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            transaction.add(android.R.id.content, fragment).addToBackStack(null).commit()
        }
    }

    private val mEditProfileListener = FragmentResultListener { key, bundle ->
        if (key == RESULT_EDIT_PROFILE) {
            mPrivateViewModel.loadUserData()
        }
    }

    override fun onResume() {
        super.onResume()
        mPrivateViewModel.loadUserData()
    }
}