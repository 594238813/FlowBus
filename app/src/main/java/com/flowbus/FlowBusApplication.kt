package com.flowbus

import android.app.Application
import com.flowbuslib.FlowBusInit

class FlowBusApplication:Application()  {

    override fun onCreate() {
        super.onCreate()

        FlowBusInit.init(this)

    }

}