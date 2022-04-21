package com.cason.eatorgasm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cason.eatorgasm.auth.GoogleAuthActivity

class EatOrgSplash : AppCompatActivity() {
    private val mShowTime = 1000

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.eat_main_logo)
        startDelayedHome()
    }

    private fun startDelayedHome() {
        Thread {
            val startTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - startTime < mShowTime) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    Log.e("", e.stackTrace.toString())
                }
            }
            intentActivity()
        }.start()
    }

    private fun intentActivity() {
        val intent = Intent(this, EatHomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}