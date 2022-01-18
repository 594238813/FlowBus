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
            val flowBusEvent = FlowBusEvent(200,"SecondActivity msg")
            postEventProcess(flowBusEvent)
        }



    }

}