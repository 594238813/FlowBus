package com.flowbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.flowbuslib.observeEvent
import kotlinx.coroutines.Dispatchers
import com.flowbuslib.FlowBusEvent
import com.flowbuslib.postEventProcess


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn).setOnClickListener {
            val flowBusEvent = FlowBusEvent(404,"MainActivity msg")
            postEventProcess(flowBusEvent)
        }

        findViewById<Button>(R.id.sec).setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }

        observeEvent<FlowBusEvent<String>>(this, Dispatchers.Main){
            Log.e("main process:","${it.code}-${it.msg}")
        }


        observeEvent<ChangeUIEvent>(this, Dispatchers.Main){
            Log.e("ChangeUIEvent:","${it.msg}")
        }



        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, OneFragment.instance)
            .commitAllowingStateLoss()

    }




}

