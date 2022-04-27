package com.cason.eatorgasm.component.base

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.cason.eatorgasm.R
import com.cason.eatorgasm.util.Utils

open class BaseDialogFragment : DialogFragment() {
    private var mOrientation = 0
    private var mLocaleType = 0
    private var mTitleId = 0

    protected var mView: View? = null
    protected var mToolbar: Toolbar? = null
    protected var mMenuItemDone: MenuItem? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocaleType = Utils.getCurrentLocaleType(resources)
        }
        mOrientation = resources.configuration.orientation
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeType = Utils.getLocaleType(newConfig.locales.get(0))
            if (mLocaleType != localeType) {
                mLocaleType = localeType
                onLocaleChanged(mLocaleType)
            }
        }
        super.onConfigurationChanged(newConfig)
    }

    protected open fun onLocaleChanged(locale: Int) {
        setTitle(mTitleId)
        mMenuItemDone?.setTitle(R.string.cm_btn_done)
    }

    fun getWindow(): Window? {
        return activity?.window
    }

    open fun setTitle(resId: Int) {
        mToolbar?.setTitle(resId)
        mTitleId = resId
    }

    protected fun setDoneButtonEnable(enable: Boolean) {
        mMenuItemDone?.isEnabled = enable
    }
}