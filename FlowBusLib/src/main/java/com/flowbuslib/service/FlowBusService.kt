package com.flowbuslib.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import com.flowbuslib.IFlowBusCallback
import com.flowbuslib.IFlowBusManager
import com.flowbuslib.bean.FlowBusEventAIDL


class FlowBusService :Service() {

    private val mRemoteCallbacks = RemoteCallbackList<IFlowBusCallback>()

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    val mBinder = object: IFlowBusManager.Stub() {

        override fun register(callback: IFlowBusCallback?) {
            if (callback != null) {
                mRemoteCallbacks.register(callback)
            }
        }

        override fun unregister(callback: IFlowBusCallback?) {
            mRemoteCallbacks.unregister(callback)
        }

        override fun postToService(event: FlowBusEventAIDL) {
            //发送 到 进程总线
            val count: Int = mRemoteCallbacks.beginBroadcast()
            for(index in 0 until count){
                mRemoteCallbacks.getBroadcastItem(index).onServiceBack(event)
            }
            mRemoteCallbacks.finishBroadcast()
        }
    }

}