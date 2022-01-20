package com.flowbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.flowbuslib.*
import kotlinx.coroutines.Dispatchers


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn).setOnClickListener {
            //进程内发送消息
            val changeUIEvent = ChangeUIEvent("MainActivity")
            postEvent(changeUIEvent)

            //夸进程发消息
//            postEventProcessAIDL(changeUIEvent)
        }

        findViewById<Button>(R.id.sec).setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }

        observeEvent<ChangeUIEvent>(this, Dispatchers.Main){
            Log.e("ChangeUIEvent:","${it.msg}")
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, OneFragment.instance)
            .commitAllowingStateLoss()

    }




}

