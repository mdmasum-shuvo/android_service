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
        val intent = Intent(this, MyService::class.java)
        button.setOnClickListener {
            startService(Intent(this, MyService::class.java))
        }


        btnstop.setOnClickListener {
            val date = Date()
            stopService(Intent(this, MyService::class.java))
            //This method returns the time in millis

        }
    }


}
