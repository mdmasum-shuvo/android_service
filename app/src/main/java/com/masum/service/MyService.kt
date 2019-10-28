package com.masum.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.os.Handler
import java.util.*
import android.R.string.cancel
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.string.cancel
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class MyService : Service() {
    val NOTIFY_INTERVAL = (5 * 1000).toLong() // 10 seconds
    // run on another Thread to avoid crash
    private val mHandler = Handler()
    // timer handling
    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null

    var count = 0;
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("run", "service started")
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimerTask!!.cancel()
        } else {
            // recreate new
            mTimer = Timer()
        }
        mTimerTask = object : TimerTask() {
            override fun run() {
                mHandler.post{
                    Log.e("run", "service task running:" + ++count)
                }
            }
        }
        mTimer!!.scheduleAtFixedRate(mTimerTask, 0, NOTIFY_INTERVAL)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mTimer!!.cancel()
        mTimerTask!!.cancel()
    }
    override fun stopService(name: Intent): Boolean {
        // TODO Auto-generated method stub
        mTimer!!.cancel()
        mTimerTask!!.cancel()
        return super.stopService(name)

    }
    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
}