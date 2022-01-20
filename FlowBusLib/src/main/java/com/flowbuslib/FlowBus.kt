package com.flowbuslib

import android.os.Process
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.flowbuslib.bean.FlowBusEventAIDL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.Serializable
import kotlin.coroutines.CoroutineContext

class FlowBus:ViewModel()


val eventFlows = HashMap<String, MutableSharedFlow<Any>>()


inline fun <reified T> getFlow(replay:Int=0,byName:String?=null): MutableSharedFlow<Any> {

    val reanName = byName?:T::class.java.name

    var temp : MutableSharedFlow<Any>? = eventFlows[reanName]
    if(temp==null){
        temp = MutableSharedFlow(replay,Int.MAX_VALUE, BufferOverflow.SUSPEND)
        eventFlows[reanName] = temp
    }

    return temp
}

//订阅
inline fun <reified T> observeEvent(context: AppCompatActivity,
                                    cDispatcher: CoroutineContext = Dispatchers.IO,
                                    replay:Int = 1,     // 粘多少
                                    noinline block: (T) ->Unit){

    val subscribeFlow = getFlow<T>(replay)
    context.lifecycleScope.launchWhenStarted {
        subscribeFlow
            .flowWithLifecycle(context.lifecycle)
            .flowOn(cDispatcher)
            .collect{
                block(it as T)
            }
    }
}

//发送消息
inline fun <reified T> postEvent(event: T, byName:String?=null){
    require(event != null) { "事件不能为空" }

    val flowbus =  FlowBusInit.getViewModelProvider()[FlowBus::class.java]

    flowbus.viewModelScope.launch {
        getFlow<T>(byName = byName).emit(event)
    }
}

//发射到 服务端
inline fun <reified T>  postEventProcessAIDL(event:T){
    require(event != null) { "事件不能为空" }
    require(event is Serializable) { "bean 要继承 Serializable" }

    val flowBusEventAIDL =  FlowBusEventAIDL()
    flowBusEventAIDL.data = event
    flowBusEventAIDL.code = 200
    flowBusEventAIDL.msg = "aaa"

    FlowBusInit.mRemoteService?.postToService(flowBusEventAIDL)
}