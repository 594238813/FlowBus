package com.flowbuslib

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.flowbuslib.FlowBusInit.KEY

class FlowReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, intent: Intent?) {
        //直接发射数据
        postEvent(intent?.getSerializableExtra(KEY) as FlowBusEvent<*>)
    }

}