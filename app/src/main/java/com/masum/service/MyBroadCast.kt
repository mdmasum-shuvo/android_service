package com.masum.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.e("run", "start broadcast")

            context!!.startService(Intent(context,MyService::class.java))

    }
}