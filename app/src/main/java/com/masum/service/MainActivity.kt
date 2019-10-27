package com.masum.service


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.app.PendingIntent
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val manager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyBroadCast::class.java)
        button.setOnClickListener {
            val date = Date()
            //This method returns the time in millis
            val timeMilli = date.getTime()
            manager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                timeMilli, AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    this,
                    1,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }


        btnstop.setOnClickListener {
            val date = Date()
            stopService(Intent(this,MyService::class.java))
            //This method returns the time in millis
            val timeMilli = date.getTime()
            manager.set(
                AlarmManager.RTC_WAKEUP,
                timeMilli,
                PendingIntent.getBroadcast(
                    this,
                    1,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            )
        }
    }


}
