package com.cason.eatorgasm.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.cason.eatorgasm.R
import com.cason.eatorgasm.define.CMEnum


class ProgressManager(private val mActivity: Activity) {

    init {
        CANCEL = mActivity.resources.getString(R.string.string_cancel)
    }

    @SuppressLint("InflateParams")
    fun setProgressBar() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.common_progress_bar, null)
        mProgressBar = dialogView.findViewById(R.id.progress_bar)
        mProgressBar.progress = 0
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(true)
        mBarDialog = builder.create()
    }

    fun setProgressMessageCircular() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.common_progress, null)
        mProgressCircular = dialogView.findViewById(R.id.progress_circular)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(true)
        mCircularDialog = builder.create()
    }

    fun setProgressCircular(rootView: ViewGroup) {
        val inflater = mActivity.layoutInflater
        val progressLayout = inflater.inflate(R.layout.common_progress, rootView)
        mProgressLayout = progressLayout.findViewById(R.id.progress_layout)
        mProgressLayout.visibility = GONE
    }

    companion object {
        private lateinit var mProgressBar: ProgressBar
        private lateinit var mProgressCircular: ProgressBar
        private lateinit var mProgressLayout: View
        private lateinit var mBarDialog: AlertDialog
        private lateinit var mCircularDialog: AlertDialog
        private var mCurrentCount: Int = 0
        private lateinit var CANCEL: CharSequence

        fun showProgress() {
            mProgressLayout.visibility = VISIBLE
        }

        fun dismissProgress() {
            mProgressLayout.visibility = GONE
        }

        fun showProgressCircular(type: CMEnum.CommonProgressType, msg: String, listener: DialogInterface.OnClickListener?) {
            if(msg != "") {
                mCircularDialog.setMessage(msg)
            }
            showProgress(mCircularDialog, type, listener)
        }

        fun dismissProgressCircular() {
            mCircularDialog.dismiss()
        }

        fun showProgressBar(type: CMEnum.CommonProgressType, listener: DialogInterface.OnClickListener?) {
            mBarDialog.setMessage("$mCurrentCount%")
            showProgress(mBarDialog, type, listener)
        }

        private fun showProgress(dialog: AlertDialog, type: CMEnum.CommonProgressType, listener: DialogInterface.OnClickListener?) {
            val progressTitleId: Int = when (type) {
                CMEnum.CommonProgressType.UPLOAD -> R.string.string_uploading
                CMEnum.CommonProgressType.ETC -> R.string.string_update
                CMEnum.CommonProgressType.NONE -> 0
            }
            if(progressTitleId != 0) {
                dialog.setTitle(progressTitleId)
            }
            if (listener != null) {
                dialog.setButton(BUTTON_NEGATIVE, CANCEL, listener)
            }
            dialog.show()
        }

        fun dismissProgressBar() {
            mBarDialog.dismiss()
            mCurrentCount = 1
        }

        fun updateByPercent(percent: Int) {
            mBarDialog.setMessage("${percent}%")
            if (mProgressBar.max > 0) {
                mProgressBar.progress = percent
            }
        }

        fun setMaxValue(value: Int) {
            mProgressBar.max = value
        }
    }
}