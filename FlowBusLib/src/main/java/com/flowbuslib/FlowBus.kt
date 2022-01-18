package com.flowbuslib

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.flowbuslib.FlowBusInit.KEY
import com.flowbuslib.FlowBusInit.RECEIVER_ACTION
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import kotlin.coroutines.CoroutineContext

class FlowBus:ViewModel()


val eventFlows = mutableMapOf<String, MutableSharedFlow<Any>>()


inline fun <reified T> getFlow(replay:Int=0,extraBufferCapacity:Int=0): MutableSharedFlow<Any> {

    var temp : MutableSharedFlow<Any>? = eventFlows[T::class.java.name]

    if(temp==null){
        temp = MutableSharedFlow(replay,extraBufferCapacity, BufferOverflow.SUSPEND)
        eventFlows[T::class.java.name] = temp
    }

    return temp
}

//订阅
inline fun <reified T> observeEvent(context: AppCompatActivity,
                                    cDispatcher: CoroutineContext = Dispatchers.IO,
                                    replay:Int=0,
                                    extraBufferCapacity:Int=0 ,
                                    noinline block: (T) ->Unit){

    val subscribeFlow = getFlow<T>(replay,extraBufferCapacity)

    context.lifecycleScope.launch {
        subscribeFlow
            .flowWithLifecycle(context.lifecycle)
            .flowOn(cDispatcher)
            .collect{
                block(it as T)
            }
    }

}

//发送消息
inline fun <reified T>  postEvent(event:T){
    require(event != null) { "事件不能为空" }

    val flowbus =  FlowBusInit.getViewModelProvider()[FlowBus::class.java]

    flowbus.viewModelScope.launch {
        getFlow<T>().emit(event)
    }
}

inline fun <reified T>  postEventProcess(event:T){
    require(event != null) { "事件不能为空" }
    require(event is Serializable) { "bean 要继承 Serializable" }

    val flowbus = FlowBusInit.getViewModelProvider()[FlowBus::class.java]

    flowbus.viewModelScope.launch {
        val it = Intent(RECEIVER_ACTION)
        it.putExtra(KEY, event as Serializable)
        FlowBusInit.appContext.sendBroadcast(it)
    }
}