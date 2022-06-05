package com.cason.eatorgasm.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.view.View
import android.widget.ProgressBar
import com.cason.eatorgasm.R
import com.cason.eatorgasm.define.CMEnum

class ProgressManager(private val mActivity: Activity) {
    private lateinit var dialogView: View

    init {
        CANCEL = mActivity.resources.getString(R.string.string_cancel)
    }

    @SuppressLint("InflateParams")
    fun setProgressBar() {
        val inflater = mActivity.layoutInflater
        dialogView = inflater.inflate(R.layout.common_progress_bar, null)
        mProgressBar = dialogView.findViewById(R.id.progress_bar)
        mProgressBar.progress = 0
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(true)
        mBarDialog = builder.create()
    }

    companion object {
        private lateinit var mProgressBar: ProgressBar
        private lateinit var mBarDialog: AlertDialog
        private var mCurrentCount: Int = 0
        private lateinit var CANCEL: CharSequence

        fun showProgressBar(type: CMEnum.CommonProgressType, listener: DialogInterface.OnClickListener?) {
            mBarDialog.setMessage("$mCurrentCount/${mProgressBar.max}")
            showProgress(mBarDialog, type, listener)
        }

        private fun showProgress(dialog: AlertDialog, type: CMEnum.CommonProgressType, listener: DialogInterface.OnClickListener?) {
            val progressTitleId: Int = when (type) {
                CMEnum.CommonProgressType.UPLOAD -> R.string.string_uploading
                CMEnum.CommonProgressType.ETC -> R.string.string_update
            }
            dialog.setTitle(progressTitleId)
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
            mBarDialog.setMessage("${percent}/${mProgressBar.max}")
            if (mProgressBar.max > 0) {
                mProgressBar.progress = percent
            }
        }

        fun setMaxValue(value: Int) {
            mProgressBar.max = value
        }
    }
}