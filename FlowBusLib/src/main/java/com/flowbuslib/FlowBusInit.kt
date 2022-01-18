package com.flowbuslib

import android.app.Application
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProvider


object FlowBusInit {

    lateinit var appContext:Application

    fun init(application: Application){
        appContext = application

        //注册广播
        val flowReceiver = FlowReceiver()
        val ift = IntentFilter("action")
        appContext.registerReceiver(flowReceiver,ift)
    }

    fun getViewModelProvider() =  ViewModelProvider(ApplicationViewModelProvider,
        ViewModelProvider.AndroidViewModelFactory.getInstance(appContext))

    val KEY = "key"
    val RECEIVER_ACTION = "action"
}