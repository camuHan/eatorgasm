package com.cason.eatorgasm.component

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.*
import android.widget.AdapterView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cason.eatorgasm.MainFragmentFactoryImpl
import com.cason.eatorgasm.R
import com.cason.eatorgasm.databinding.HomeMainViewBinding
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.util.Utils
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EatHomeActivity : AppCompatActivity(), EatHomeActivityActionListener {

    private lateinit var mBinding: HomeMainViewBinding
//    private lateinit var mUpPanelBinding: HomeBrowserUpPanelViewBinding
    private lateinit var mBottomSheetDialog: BottomSheetDialog

//    private lateinit var mBottomSheetDialogAdapter: BottomSheetDialogAdapter
    private lateinit var boardFragment: BoardFragment
    private lateinit var browserFragment: BoardFragment
    private lateinit var fileTypeFragment: BoardFragment
    private lateinit var privateFragment: PrivateFragment

    private var mHomeToast: Toast? = null
    private var mHomeToastMsg: String? = null

    private var searchMenu: MenuItem? = null
    private var newFileMenu: MenuItem? = null

    private var tabIconList: ArrayList<Int>? = null

    private var mMenuPopup: PopupWindow? = null
    private var mStrRecoveryPath: String? = null
    private var mIsRecoveryCompleted = false
    private var mMILicenseKey: String? = null

    private val mHomeViewModel: HomeViewModel by viewModels()
    override fun onCreate(saveInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = MainFragmentFactoryImpl()
        super.onCreate(saveInstanceState)

        createDataBase()
        createProgressDialog()
        setLayout()

        setHomeBottomSheetDialogObserver()
        setOpenObserver()

        tabIconList = getResourceIdList(R.array.home_tab_button_drawable)
        initViewPager()

//        setSupportActionBar(mBinding.homeToolbarLayout.homeToolbar)
        showDisplayLogoInActionBar(true)

        mHomeViewModel.loadUserData()
    }

    override fun getUserInfoLiveData(): LiveData<UserInfoModel?> {
        return mHomeViewModel.getUserLiveData()
    }

    private fun createDataBase() {
//        HomeRoomDataBase.getInstance(applicationContext)
//        OfficeRoomDataBase.getInstance(applicationContext)
    }

    private fun createProgressDialog() {
//        HomeFileProgress(this).setProgressBar()
//        HomeFileProgress(this).setProgressBasic()
    }

    private fun setLayout() {
        mBinding = HomeMainViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun setBottomSheet() {
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        mUpPanelBinding = HomeEatUpPanelViewBinding.inflate(inflater, null, false)
//        mBottomSheetDialog = BottomSheetDialog(this)
//        mBottomSheetDialog.setContentView(mUpPanelBinding.root)
//        mBottomSheetDialog.dismissWithAnimation = true
    }

    private fun setHomeBottomSheetDialogObserver() {
//        mHomeViewModel.onHomeBottomSheetDialogEvent.observe(this, { it ->
//            it.getContentIfNotHandled()?.let {
//                if (it) {
//                    showBottomSheetDialog()
//                } else {
//                    hideBottomSheetDialog()
//                }
//            }
//        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showBottomSheetDialog() {
//        mBottomSheetDialogAdapter.notifyDataSetChanged()

        Handler(Looper.getMainLooper()).post{
            mBottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            mBottomSheetDialog.show()
        }
    }

    private fun hideBottomSheetDialog() {
        mBottomSheetDialog.dismiss()
    }

    private fun setOpenObserver() {
//        mHomeViewModel.onOpenEvent.observe(this, { it ->
//            it.getContentIfNotHandled()?.let {
////                openDocument(it)
//            }
//        })
    }

//    private fun setBottomSheetDialog(contract: HomeContract, homeFileItem: HomeFileItem?) {
//        setBottomSheet()
//        mBottomSheetDialogAdapter = BottomSheetDialogAdapter(contract)
//
//        val menuIdList: IntArray
//        val drawableIdList: ArrayList<Drawable>
//        val stringList: ArrayList<String>
//        if(homeFileItem != null) {
//            mBottomSheetDialogAdapter.mIsFavorite = homeFileItem.isFavorite
//            mBottomSheetDialogAdapter.mIsDirectory = homeFileItem.isDirectory
//            mUpPanelBinding.model = homeFileItem
//
//            mUpPanelBinding.homeBrowserUpPanelFileInfo.homeBrowserFileListLayout.visibility =
//                View.VISIBLE
//            mUpPanelBinding.homeBrowserUpPanelCheckedInfo.visibility = View.GONE
//            menuIdList = getBrowserBottomSheetDialogIdList()
//            drawableIdList = getDrawableList(R.array.home_browser_bottom_sheet_dialog_drawable)
//            stringList = getStringList(R.array.home_browser_bottom_sheet_dialog_string)
//        } else {
//            mBottomSheetDialogAdapter.mIsFavorite = false
//            mBottomSheetDialogAdapter.mIsDirectory = false
//
//            mUpPanelBinding.homeBrowserUpPanelFileInfo.homeBrowserFileListLayout.visibility =
//                View.GONE
//            mUpPanelBinding.homeBrowserUpPanelCheckedInfo.visibility = View.VISIBLE
//            menuIdList = getCheckBoxBottomSheetDialogIdList()
//            drawableIdList = getDrawableList(R.array.home_checkbox_bottom_sheet_dialog_drawable)
//            stringList = getStringList(R.array.home_checkbox_bottom_sheet_dialog_string)
//        }
//
//        mUpPanelBinding.homeBrowserUpPanelFileInfo.backBg = ResourcesCompat.getDrawable(resources, R.drawable.home_sliding_panel_round_shape, null)
//        mUpPanelBinding.homeBrowserUpPanelFunctionList.adapter = mBottomSheetDialogAdapter
//        mUpPanelBinding.homeBrowserUpPanelFileInfo.homeBrowserFileMore.visibility = View.GONE
//        mUpPanelBinding.homeBrowserUpPanelFunctionList.itemAnimator = null
//
//        val menuArray = ArrayList<BottomSheetDialogItem>()
//        for (i in 0 until drawableIdList.size) {
//            menuArray.add(BottomSheetDialogItem(menuIdList[i], drawableIdList[i], stringList[i]))
//        }
//
//        mBottomSheetDialogAdapter.mItems = menuArray
//    }

    public override fun onPause() {
        dismissPopupWindow()
        super.onPause()
    }

    public override fun onResume() {
        onStartRecovery()
        super.onResume()
    }

    public override fun onDestroy() {
        Utils.unbindDrawables(findViewById(R.id.home_root_view))
//        HomeRoomDataBase.clearDB()
        super.onDestroy()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.home_main_menu, menu)
//        searchMenu = menu.findItem(R.id.home_main_menu_search)
//        newFileMenu = menu.findItem(R.id.home_main_menu_newfile)
//        newFileMenu?.isVisible = B2BConfig.USE_EDITOR()
//
//        val selectedPosition = mBinding.homeTypeTabLayout.selectedTabPosition
//        if(selectedPosition == 0) {
//            newFileMenu?.setIcon(R.drawable.browser_ico_new_document_gray)
//        } else {
//            newFileMenu?.setIcon(R.drawable.browser_ico_new_document)
//        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.home_main_menu_newfile -> {
//                showActionBarNewFormPopup()
//            }
//            R.id.home_main_menu_settings -> {
//                onSetting()
//            }
//            else -> {
//                return super.onOptionsItemSelected(item)
//            }
//        }
        return true // consume it here
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        when (requestCode) {
//            PODefine.Request.DIALOG_AUTO_RECOVERY -> {
//                mIsRecoveryCompleted = true
//                if (resultCode == RESULT_OK) {
//                    val openIntent = Intent(this, OfficeLauncherActivity::class.java)
//                    openIntent.putExtra(CMDefine.InternalCmdType.DM_CMD_KEYSTR, CMDefine.InternalCmdType.DM_INTCMD_NONE)
//                    openIntent.putExtra(CMDefine.ExtraKey.OPEN_FILE, mStrRecoveryPath)
//                    startActivity(openIntent)
//                }
//            }
//            PODefine.Request.DIALOG_OPEN_DOCUMENT -> {
//                var errMsgId = 0
//                when (resultCode) {
//                    PODefine.Result.RESULT_FILE_SYNC_CANCEL -> errMsgId = R.string.fm_err_canceled_by_user
//                    PODefine.Result.RESULT_FILE_SYNC_FAIL -> errMsgId = R.string.fm_err_src_removed
//                }
//                if (errMsgId > 0) onToastMessage(getString(errMsgId))
//            }
//            PODefine.Request.DIALOG_CHECK_LICENSE -> {
//                if (resultCode == RESULT_OK) {
//                    if (B2BConfig.USE_ExtraFont()) {
//                        fontLoadManager = ExtraFontLoadManager()
//                        fontLoadManager!!.loadExtraFont(this)
//                    }
//                } else {
//                    finish()
//                }
//            }
//            PODefine.Request.DIALOG_OPEN_SETTINGS -> {
//                recentFragment.notifyDataSetChanged()
//                browserFragment.notifyDataSetChanged()
//                fileTypeFragment.notifyDataSetChanged()
//                favoriteFragment.notifyDataSetChanged()
//            }
//            else -> {
//            }
//        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        val bCtrlPressed = event.isCtrlPressed

        when (keyCode) {
            KeyEvent.KEYCODE_N ->{
                if(bCtrlPressed) {
                    showActionBarNewFormPopup()
                    return true
                }
            }

        }
        return super.onKeyUp(keyCode, event)
    }

//    override fun onLocaleChanged(a_locale: Int) {
//        super.onLocaleChanged(a_locale)
//        setText()
//        invalidateOptionsMenu()
//    }

//    override fun onOrientationChanged(a_orientation: Int) {
//        super.onOrientationChanged(a_orientation)
//        dismissPopupWindow()
//    }

    private fun makePopupWindow(popupView: View, popupWidth: Int): PopupWindow {
        val menuPopup = PopupWindow(popupView, popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        menuPopup.isFocusable = true
        menuPopup.isTouchable = true
        menuPopup.isOutsideTouchable = true
        menuPopup.isClippingEnabled = false
        menuPopup.setOnDismissListener(mMenuPopupDismissListener)
        menuPopup.elevation = 10.0f
        return menuPopup
    }

    private fun initViewPager(){
        boardFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, BoardFragment::class.java.name) as BoardFragment
        browserFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, BoardFragment::class.java.name) as BoardFragment
        fileTypeFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, BoardFragment::class.java.name) as BoardFragment
        privateFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, PrivateFragment::class.java.name) as PrivateFragment

        val adapter = PageAdapter(this) // PageAdapter 생성
        adapter.addItems(boardFragment)
        adapter.addItems(browserFragment)
        adapter.addItems(fileTypeFragment)
        adapter.addItems(privateFragment)

        val tabTextList = getStringList(R.array.home_tab_title_string)

        mBinding.homeViewPager.adapter = adapter
        mBinding.homeViewPager.isUserInputEnabled = false
//        mBinding.homeViewPager.offscreenPageLimit = FRAGMENT_COUNT - 1
        mBinding.homeViewPager.setPageTransformer(null)

        TabLayoutMediator(mBinding.homeTypeTabLayout, mBinding.homeViewPager, false, false) { tab, position ->
            tabIconList?.let { tab.setIcon(it[position]) }
            tab.text = tabTextList[position]
            tab.id = position
        }.attach()

        mBinding.homeTypeTabLayout.addOnAttachStateChangeListener(object :
            View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View?) {
//                mHomeViewModel.startRecentTab()
//                mHomeViewModel.startBrowserTab()
//                mHomeViewModel.startFavoriteTab()
            }

            override fun onViewDetachedFromWindow(p0: View?) {
            }

        })

        mBinding.homeTypeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.id) {
//                    0 -> {
//                        showDisplayLogoInActionBar(true)
//                        recentFragment.notifyDataSetChanged()
//                    }
//                    1 -> {
//                        supportActionBar?.setTitle(R.string.po_title_browser)
//                        showDisplayLogoInActionBar(false)
//                        browserFragment.notifyDataSetChanged()
//                    }
//                    2 -> {
//                        supportActionBar?.setTitle(R.string.po_title_types)
//                        showDisplayLogoInActionBar(false)
//
//                    }
//                    3 -> {
//                        supportActionBar?.setTitle(R.string.po_title_favorite)
//                        showDisplayLogoInActionBar(false)
//                        favoriteFragment.notifyDataSetChanged()
//                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.id) {
                    1 -> {
//                        mHomeViewModel.endCheckMode()
                    }
                }
            }
        })

    }

    private fun getResourceIdList(arrayId: Int): ArrayList<Int> {
        return Utils.getResouceIdList(applicationContext, arrayId)
    }

    private fun getStringList(arrayId: Int): ArrayList<String> {
        val stringList = ArrayList<String>()
        val list = Utils.getResouceIdList(applicationContext, arrayId)
        list.forEach {
            if(it != null) {
                stringList.add(getString(it))
            }
        }

        return stringList
    }

    private fun getDrawableList(arrayId: Int): ArrayList<Drawable> {
        val drawableList = ArrayList<Drawable>()
        val list = Utils.getResouceIdList(applicationContext, arrayId)
        list.forEach {
            if(it != null) {
                val drawable = ResourcesCompat.getDrawable(resources, it, null)
                if(drawable != null) {
                    drawableList.add(drawable)
                }
            }
        }

        return drawableList
    }

    private fun getBrowserBottomSheetDialogIdList(): IntArray {
        return IntArray(1)//resources.getIntArray(R.array.home_browser_bottom_sheet_dialog_id)
    }

    private fun getCheckBoxBottomSheetDialogIdList(): IntArray {
        return IntArray(1)//resources.getIntArray(R.array.home_checkbox_bottom_sheet_dialog_id)
    }

    fun showDisplayLogoInActionBar(show: Boolean) {
//        val overflowIconColor = if (show) {
//            changeStatusBar(R.color.office_theme_viewer_color_primary_dark)
//            supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.view_mode_actionbar_color, null)))
//            supportActionBar?.setLogo(R.drawable.browser_bi_logo)
//            searchMenu?.isVisible = false
//            newFileMenu?.setIcon(R.drawable.browser_ico_new_document_gray)
//            mBinding.homeToolbarLayout.homeToolbar.isSelected = false
//            resources.getColor(R.color.home_actionbar_more_icon_color, null)
//        } else {
//            changeStatusBar(R.color.office_theme_color_primary_dark)
//            supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.office_theme_color_primary, null)))
//            searchMenu?.isVisible = true
//            newFileMenu?.setIcon(R.drawable.browser_ico_new_document)
//            mBinding.homeToolbarLayout.homeToolbar.isSelected = true
//            resources.getColor(R.color.white, null)
//        }
//
//        mBinding.homeToolbarLayout.homeToolbar.overflowIcon?.setTint(overflowIconColor)
//        supportActionBar?.setDisplayUseLogoEnabled(show)
//        supportActionBar?.setDisplayShowHomeEnabled(show)
//        supportActionBar?.setDisplayShowTitleEnabled(!show)
    }

    private fun changeStatusBar(resId: Int) {
//        if (!Utils.isAboveM() || resId == 0) {
//            return
//        }
//
//        // check dark mode & change status bar
//        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_YES) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                val value = if (resId == R.color.office_theme_viewer_color_primary_dark) {
//                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
//                } else {
//                    0
//                }
//                window.insetsController?.setSystemBarsAppearance(value, value)
//            } else {
//                window.decorView.systemUiVisibility = if (resId == R.color.office_theme_viewer_color_primary_dark) {
//                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                } else {
//                    0
//                }
//            }
//        }
//
//        window.statusBarColor = resources.getColor(resId, null)
    }

    private fun setText() {

        val tabTextList = getStringList(R.array.home_tab_title_string)
        tabTextList.forEachIndexed { index, s ->
            mBinding.homeTypeTabLayout.getTabAt(index)?.text = s
        }
    }

    private fun dismissPopupWindow() {
        if (mMenuPopup != null) {
            mMenuPopup!!.dismiss()
            mMenuPopup = null
        }
    }

    private fun setActionBarList() {
//        mActionList = ArrayList()
//        mActionList!!.add(FileActionBarItem(PODefine.MenuId.NEW_DOCX, R.drawable.p7_fb_ico_file_docx, getString(R.string.po_menu_item_new_doc)))
//        mActionList!!.add(FileActionBarItem(PODefine.MenuId.NEW_XLSX, R.drawable.p7_fb_ico_file_xlsx, getString(R.string.po_menu_item_new_xls)))
//        mActionList!!.add(FileActionBarItem(PODefine.MenuId.NEW_PPTX, R.drawable.p7_fb_ico_file_pptx, getString(R.string.po_menu_item_new_ppt)))
//        if (B2BConfig.USE_HWP_Support() == HWP_SUPPORT.EDITOR) {
//            mActionList!!.add(FileActionBarItem(PODefine.MenuId.NEW_HWP, R.drawable.p7_fb_ico_file_hwp, getString(R.string.po_menu_item_new_hwp)))
//        }
//        mActionList!!.add(FileActionBarItem(PODefine.MenuId.NEW_TEXT, R.drawable.p7_fb_ico_file_txt, getString(R.string.po_menu_item_new_txt)))
    }

    private fun showActionBarNewFormPopup() {
//        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        setActionBarList()
//        val adapter = homeNewFileAdapter
//        val popupView = inflater.inflate(R.layout.fm_actionbar_popup, null)
//        val newFormRecyclerView: RecyclerView = popupView.findViewById(R.id.fm_more_list)
//        newFormRecyclerView.adapter = adapter
//        var popupWindowWidth = resources.getDimensionPixelSize(R.dimen.home_new_form_popup_width)
//
//        popupWindowWidth = fitToTheItemWidth(popupWindowWidth)
//        val nMenuButtonPos = IntArray(2)
//        mBinding.homeToolbarLayout.homeToolbar.getLocationInWindow(nMenuButtonPos)
//        val popupPositionX = Utils.getScreenWidthPixels(this) - popupWindowWidth
//        val popupPositionY = nMenuButtonPos[1] + mBinding.homeToolbarLayout.homeToolbar.height
//        mMenuPopup = makePopupWindow(popupView, popupWindowWidth)
//        mMenuPopup!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.po_home_new_form_bg, null)))
//        mMenuPopup!!.showAtLocation(mBinding.homeToolbarLayout.homeToolbar, Gravity.NO_GRAVITY, popupPositionX, popupPositionY)
//        mMenuPopup!!.update()
    }

    private fun fitToTheItemWidth(popupWindowWidth: Int): Int {
        var width = popupWindowWidth
        val tvTemp = TextView(this)

        tvTemp.width = width
        tvTemp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.33f)

        val imgMargin = Utils.dipToPixel(this, 1.0f)
        val txtMargin = Utils.dipToPixel(this, 4.0f)
        val nImageWidth = Utils.dipToPixel(this, 42.67f) //64;
        var nItemWidth = 0
//        val itemTotalNumber = mActionList!!.size
//        for (i in 0 until itemTotalNumber) {
//            val item = mActionList!![i]
//            var nItemTextWidth = 0
//            //IconWidth
//            if (item.m_nMenuId >= PODefine.MenuId.NEW_DOC && item.m_nMenuId <= PODefine.MenuId.NEW_TEXT) nItemTextWidth += imgMargin + nImageWidth
//            //Text width
//            nItemTextWidth += tvTemp.paint.measureText(item.m_strText).toInt()
//            //Text spacing
//            nItemTextWidth += item.m_strText.length
//            //Margin
//            nItemTextWidth += 2 * txtMargin
//            if (nItemWidth < nItemTextWidth) nItemWidth = nItemTextWidth
//        }
        if (width < nItemWidth) {
            width = nItemWidth
        }
        return width
    }

    private fun onToastMessage(message: String) {
        mHomeToastMsg = message
        if (mHomeToastMsg != null && mHomeToastMsg!!.isNotEmpty())
            runOnUiThread(mRunToastMsg)
    }

    //BB 에서 사용하여 public 유지
    public fun onSetting() {
//        startActivityForResult(Intent(this, SettingActivity::class.java), PODefine.Request.DIALOG_OPEN_SETTINGS)
    }

    // 자동복구가 나타날 시점 변경
    private fun onStartRecovery() {
//        if (!B2BConfig.USE_AutoRecovery(this) || mIsRecoveryCompleted) {
//            return
//        }
//        val file = PLFile(CMDefine.OfficeDefaultPath.getRecoveryPath())
//        if (!file.exists()) {
//            CMLog.e(LOG_TAG, "[onStartRecovery] " + file.absolutePath + " does not exist.")
//            return
//        }
//
//        val files = file.listFiles()
//        if (files == null || files.isEmpty()) {
//            CMLog.e(LOG_TAG, "[onStartRecovery] files == null")
//            return
//        }
//
//        var recoveryFilePath: String?
//        mStrRecoveryPath = null
//        for (fp in files) {
//            recoveryFilePath = RuntimeConfig.getInstance().getStringPreference(applicationContext, fp.absolutePath, null)
//            if (recoveryFilePath != null) {
//                mStrRecoveryPath = fp.absolutePath
//                break
//            }
//        }
//
//        if (mStrRecoveryPath == null) {
//            return
//        }
//
//        val intent = Intent(this, OfficeRecoveryActivity::class.java)
//        startActivityForResult(intent, PODefine.Request.DIALOG_AUTO_RECOVERY)
    }

    private val mRunToastMsg = Runnable {
        if (mHomeToast == null) mHomeToast = Toast.makeText(this@EatHomeActivity, mHomeToastMsg, Toast.LENGTH_SHORT) else mHomeToast!!.setText(mHomeToastMsg)
        mHomeToast!!.show()
    }

    private val mMenuPopupDismissListener = PopupWindow.OnDismissListener { mMenuPopup = null }
    private val mNewFormItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//        dismissPopupWindow()
//        val actionbar = mActionList!![position]
//        var strSavePath = B2BConfig.GET_UserStartupFolderPath(baseContext)
//        if (TextUtils.isEmpty(strSavePath)) {
//            strSavePath = CMDefine.OfficeDefaultPath.getBrowserRootPath()
//        }
//        if (!FileUtils.isExist(strSavePath)) {
//            FileUtils.makeDirectory(strSavePath)
//        }
//        var contentType = CMDefine.ContentType.DOC
//        when (actionbar.m_nMenuId) {
//            PODefine.MenuId.NEW_DOC -> contentType = CMDefine.ContentType.DOC
//            PODefine.MenuId.NEW_DOCX -> contentType = CMDefine.ContentType.DOCX
//            PODefine.MenuId.NEW_XLS -> contentType = CMDefine.ContentType.XLS
//            PODefine.MenuId.NEW_XLSX -> contentType = CMDefine.ContentType.XLSX
//            PODefine.MenuId.NEW_PPT -> contentType = CMDefine.ContentType.PPT
//            PODefine.MenuId.NEW_PPTX -> contentType = CMDefine.ContentType.PPTX
//            PODefine.MenuId.NEW_TEXT -> contentType = CMDefine.ContentType.TXT
//            PODefine.MenuId.NEW_HWP -> contentType = CMDefine.ContentType.HWP
//        }
//        val intent = officeHomeSwitchIntent
//        intent.putExtra(PODefine.ExtraKey.LIST_TYPE, PODefine.Type.TEMPLATE)
//        intent.putExtra(CMDefine.ExtraKey.CONTENT_TYPE, contentType)
//        intent.putExtra(FMDefine.ExtraKey.CURRENT_FILE, strSavePath)
//        startActivityForResult(intent, PODefine.Request.DIALOG_TEMPLATE_LIST)
    }

//    private val homeNewFileAdapter: HomeNewFileAdapter
//        get() = HomeNewFileAdapter(this, R.layout.fm_actionbar_listitem, mActionList, mNewFormItemClickListener)
//    private val officeHomeSwitchIntent: Intent
//        get() = Intent(this, OfficeHomeSwitch::class.java)
//    private fun checkLicense(licenseStatus: Int) {
//        if (licenseStatus == LicenseStatus.UPDATE) {
//            if (NetworkUtil.isConnected(baseContext)) {
//                mLicenseLoaderManager?.saveLicenseKey(mLicenseLoaderManager?.restoreLicenseKey())
//                mLicenseLoaderManager?.start(null)
//            } else {
//                showAlertDialog(resources.getString(R.string.license_error_network))
//            }
//        } else {
//            val intent = Intent(this@PolarisHomeActivity, LicenseActivity::class.java)
//            if (B2BConfig.USE_MIServerLicenseKey()) {
//                intent.putExtra("MI_LICENSEKEY", mMILicenseKey)
//            }
//            startActivityForResult(intent, PODefine.Request.DIALOG_CHECK_LICENSE)
//        }
//    }

//    private fun showAlertDialog(msg: String) {
//        val mAlertDialog = AlertDialog.Builder(this)
//            .setTitle(R.string.license_checking)
//            .setMessage(msg)
//            .setPositiveButton(R.string.cm_btn_ok) { _, _ -> checkLicense(0) }.create()
//        mAlertDialog.setOwnerActivity(this)
//        mAlertDialog.setCanceledOnTouchOutside(false)
//        mAlertDialog.show()
//    }

//    private fun openDocument(fileListItem: HomeFileItem) {
//        if (fileListItem.strPath == "") {
//            val assetManager = this.resources.assets
//            try {
//                val strLang = Locale.ENGLISH.language
//                var samplePath: String? = null
//                when {
//                    fileListItem.strExt.compareTo("docx", ignoreCase = true) == 0 -> {
//                        samplePath = resources.getString(R.string.fm_sample_word)
//                    }
//                    fileListItem.strExt.compareTo("pptx", ignoreCase = true) == 0 -> {
//                        samplePath = resources.getString(R.string.fm_sample_slide)
//                    }
//                    fileListItem.strExt.compareTo("xlsx", ignoreCase = true) == 0 -> {
//                        samplePath = resources.getString(R.string.fm_sample_sheet)
//                    }
//                }
//                samplePath = String.format(samplePath!!, strLang)
//                val iStream = assetManager.open(samplePath)
//                val defaultDir = PLFile(CMDefine.OfficeDefaultPath.getUITempPath())
//
//                if (!defaultDir.exists() && !defaultDir.mkdirs()) {
//                    CMLog.e(LOG_TAG, "[openDocument] " + defaultDir.absolutePath + " could not create.")
//                }
//
//                val newFile = PLFile(PLFile.createTempFile("sample", "." + fileListItem.strExt, defaultDir).absolutePath)
//                val oStream = PLFileOutputStream(newFile)
//                val buf = ByteArray(1024)
//                do {
//                    val numRead = iStream.read(buf)
//                    if (numRead <= 0) break
//                    oStream.write(buf, 0, numRead)
//                } while (true)
//                iStream.close()
//                oStream.close()
//                val strFilePath = newFile.absolutePath
//                val intent = Intent(this, OfficeLauncherActivity::class.java)
//                intent.putExtra(CMDefine.InternalCmdType.DM_CMD_KEYSTR, CMDefine.InternalCmdType.DM_INTCMD_NONE)
//                intent.putExtra(CMDefine.ExtraKey.NEW_FILE, CMDefine.OfficeDefaultPath.getBrowserRootPath() + fileListItem.strName)
//                if (strFilePath.isNotEmpty()) {
//                    intent.putExtra(CMDefine.InternalCmdType.DM_CMD_KEYSTR, CMDefine.InternalCmdType.DM_INTCMD_MANUAL)
//                    intent.putExtra(CMDefine.ExtraKey.TEMPLATE_FILE, strFilePath)
//                }
//                intent.putExtra(CMDefine.ExtraKey.OPEN_START_TIME, CMLog.startTimeTrace("OPEN")) // 로딩성능 측정용
//                startActivityForResult(intent, PODefine.Request.DIALOG_OPEN_DOCUMENT)
//                return
//            } catch (e: IOException) {
//                CMLog.trace(e.stackTrace)
//                return
//            }
//        }
//        val intent = Intent(this, OfficeLauncherActivity::class.java)
//        intent.putExtra(CMDefine.InternalCmdType.DM_CMD_KEYSTR, CMDefine.InternalCmdType.DM_INTCMD_NONE)
//        intent.putExtra(CMDefine.ExtraKey.OPEN_FILE, fileListItem.strPath)
//        intent.putExtra(CMDefine.ExtraKey.HOME_RECENT_FILE, true)
//        startActivityForResult(intent, PODefine.Request.DIALOG_OPEN_DOCUMENT)
//    }

//    override fun onCommand(commandType: HomeEnum.HomeCommand, vararg args: Any?) {
//        when(commandType) {
//            HomeEnum.HomeCommand.eHome_File_Item_Clicked -> {
//                mHomeViewModel.openDocument(args[0] as HomeFileItem)
//            }
//            HomeEnum.HomeCommand.eHome_File_Item_LongClicked -> {
//                setBottomSheetDialog(args[0] as HomeContract,
//                    null
//                )
//            }
//            HomeEnum.HomeCommand.eHome_File_Button_Clicked -> {
//                val homeFileItem = args[0] as HomeFileItem
//                setBottomSheetDialog(args[2] as HomeContract, homeFileItem)
//            }
//            HomeEnum.HomeCommand.eHome_File_Item_Checked -> {
//                val checkedItemCount = String.format(getString(R.string.str_home_checkbox_bottom_sheet_dialog_title, args[0] as Int))
//                mUpPanelBinding.count = checkedItemCount
//            }
//            else -> {}
//        }
//    }

    override fun onBackPressed() {
//        when (mBinding.homeTypeTabLayout.selectedTabPosition) {
//            1 -> {
//                if (browserFragment.onBackPressed()) {
//                    return
//                } else {
//                    mBinding.homeViewPager.setCurrentItem(0, false)
//                    return
//                }
//            }
//            2 -> {
//                if (!fileTypeFragment.onBackPressed()) {
//                    mBinding.homeViewPager.setCurrentItem(0, false)
//                    return
//                }
//            }
//            3 -> {
//                if (!favoriteFragment.onBackPressed()) {
//                    mBinding.homeViewPager.setCurrentItem(0, false)
//                    return
//                }
//            }
//        }

        super.onBackPressed()
    }

    inner class PageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){
        private var fragments : ArrayList<Fragment> = ArrayList()

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemCount(): Int = fragments.size

        fun addItems(fragment: Fragment){
            fragments.add(fragment)
        }
    }
}