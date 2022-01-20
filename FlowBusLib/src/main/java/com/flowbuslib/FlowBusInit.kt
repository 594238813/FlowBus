package com.flowbuslib

import android.app.Application
import android.content.*
import android.os.*
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.flowbuslib.bean.FlowBusEventAIDL
import com.flowbuslib.service.FlowBusService


object FlowBusInit {

    lateinit var appContext:Application

    var mRemoteService: IFlowBusManager? = null

    fun init(application: Application){
        appContext = application


        val connection = object:ServiceConnection{

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mRemoteService = IFlowBusManager.Stub.asInterface(service)
                mRemoteService?.register(iFlowBusCallback)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                mRemoteService?.unregister(iFlowBusCallback)
                mRemoteService = null
            }
        }

        //绑定远程服务
        val intent = Intent(appContext, FlowBusService::class.java)
        appContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)

    }

    val iFlowBusCallback = object: IFlowBusCallback.Stub() {
        override fun onServiceBack(info: FlowBusEventAIDL) {

            Log.e("onServiceBack","收到来自service的消息：${info.msg} - ${info.data}-${info.data.javaClass.name}")

            postEvent(info.data,info.data.javaClass.name)
        }
    }

    fun getViewModelProvider() = ViewModelProvider(ApplicationViewModelProvider,
        ViewModelProvider.AndroidViewModelFactory.getInstance(appContext))

}