package com.flowbus

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.flowbuslib.*

class SecondActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.btn).setOnClickListener {
            //换一个进程   发消息
            val changeUIEvent = ChangeUIEvent("SecondActivity msg")
            postEventProcessAIDL(changeUIEvent)
        }
    }

}