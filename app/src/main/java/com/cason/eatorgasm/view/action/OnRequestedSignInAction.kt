package com.cason.eatorgasm.view.action

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface OnRequestedSignInAction {
    fun onRequestedSignIn(googleResultLauncher: ActivityResultLauncher<Intent>)
}