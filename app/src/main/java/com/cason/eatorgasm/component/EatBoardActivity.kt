package com.cason.eatorgasm.component

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.cason.eatorgasm.R
import com.cason.eatorgasm.adapter.BoardImageListAdapter
import com.cason.eatorgasm.component.contract.ComponentContract
import com.cason.eatorgasm.databinding.BoardActivityBinding
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine
import com.cason.eatorgasm.define.EatDefine.BundleKey.BOARD_INFO_MODEL
import com.cason.eatorgasm.define.EatDefine.TransitionName.IMAGE_TRANSITION
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.ProgressManager
import com.cason.eatorgasm.viewmodel.screen.BoardViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


@AndroidEntryPoint
class EatBoardActivity : AppCompatActivity(), ComponentContract {
    private lateinit var mBinding: BoardActivityBinding
    private val mBoardViewModel: BoardViewModel by viewModels()

    private lateinit var mBoardImageListAdapter: BoardImageListAdapter
    private lateinit var mBoardInfoModel: BoardInfoModel

    private val mEditBoardListener = FragmentResultListener { key, bundle ->
        if (key == EatDefine.Request.REQUEST_KEY) {
            getBoardData(bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBoardInfoModel = intent.getSerializableExtra(BOARD_INFO_MODEL) as BoardInfoModel

        setLayout()

        initTransparentActionBar()
        initBoardLayout()
        setObservers()
    }

    private fun setLayout() {
        mBinding = BoardActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.board = mBoardInfoModel
        mBinding.clBoardProfile.tvUserName.text = mBoardInfoModel.name
        Glide.with(applicationContext).load(mBoardInfoModel.photoUrl).circleCrop().into(mBinding.clBoardProfile.ivUserThumb)
    }

    private fun initTransparentActionBar() {
        mBinding.boardToolbarLayout.background.alpha = 0
        // actionbar 투명 효과 적용
        mBinding.boardScrollview.setOnScrollChangeListener { view, x, y, oldx, oldy ->
            if(y in 0..1299) {
                val ratio = y / (1300f)
                CMLog.e("HSH", "test = $ratio")
                mBinding.boardToolbarLayout.background.alpha = (ratio * 255).toInt()
            }
        }
    }

    private fun initBoardLayout() {
        mBoardImageListAdapter = BoardImageListAdapter(applicationContext, this)

        mBinding.boardViewPager.adapter = mBoardImageListAdapter
        mBinding.boardViewPager.currentItem = 0
        mBinding.boardViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mBinding.boardViewPager.offscreenPageLimit = 3
        mBoardImageListAdapter.setItems(mBoardInfoModel.contentsList)

        // transition
        postponeEnterTransition();
        mBinding.boardViewPager.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        mBinding.boardViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 스크롤뷰와 함께 viewpager 사용 시, viewpager의 가로스크롤이 잘 먹지 않을때 사용
                mBinding.boardViewPager.parent.requestDisallowInterceptTouchEvent(true)
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

        // viewpager2 페이지 효과
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))
        transform.addTransformer { view: View, fl: Float ->
            val v = 1 - abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        }
        mBinding.boardViewPager.setPageTransformer(transform)

        TabLayoutMediator(mBinding.boardTabLayout, mBinding.boardViewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        mBinding.ibBoardListMore.setOnClickListener { view ->
            val boardInfoModel = mBinding.board as BoardInfoModel
            setPopupMenu(boardInfoModel, view)
        }
    }

    private fun setObservers() {
        mBoardViewModel.getBoardLiveData().observe(this) { boardInfoModel ->
            mBinding.board = boardInfoModel
            mBinding.clBoardProfile.tvUserName.text = boardInfoModel?.name
            Glide.with(applicationContext).load(boardInfoModel?.photoUrl).circleCrop().into(mBinding.clBoardProfile.ivUserThumb)
            mBoardImageListAdapter.setItems(boardInfoModel.contentsList)
            ProgressManager.dismissProgress()
        }
    }

    private fun getBoardData(bundle: Bundle?) {
        val board = bundle?.getSerializable(BOARD_INFO_MODEL) as BoardInfoModel
        val boardId = board.boardId
//        val boardId = bundle?.getString(EatDefine.BundleKey.BOARD_ID, "")
        if(boardId != null && boardId != "") {
            mBoardViewModel.getBoardDataByBoardId(boardId)
            ProgressManager.showProgress()
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
        val popupMenu = PopupMenu(applicationContext, view)
        menuInflater.inflate(R.menu.board_more_menu, popupMenu.menu)
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
        supportFragmentManager.setFragmentResultListener(EatDefine.Request.REQUEST_KEY, this, mEditBoardListener)
        dialog.show(supportFragmentManager, "board");
    }

    override fun onBackPressed() {
        val holder = (mBinding.boardViewPager.get(0) as RecyclerView).findViewHolderForAdapterPosition(mBinding.boardViewPager.currentItem)
        holder?.itemView?.findViewById<ImageView>(R.id.iv_board_image_list_image)?.transitionName = IMAGE_TRANSITION
        super.onBackPressed()
    }
}