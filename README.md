# FlowBus
FlowBus for Android 消息总线，支持切换协程，支持跨进程

## 使用
```
//application
FlowBusInit.init(this)

//发送
postEvent(ChangeUIEvent(" msg"))

//跨进程发送
val flowBusEvent = FlowBusEvent(200,"SecondActivity msg")
postEventProcess(flowBusEvent)

//订阅
//可以配置 粘性数量，切换协程等
observeEvent<ChangeUIEvent>(this){
    Log.e("ChangeUIEvent:","${it.msg}")
}
```
整个依赖于SharedFlow，天生的可配置粘性，并且支持切换线程。  
至于跨进程，跨进程的方式也就几种，我用了相对方便的方式，广播  
每个进程初始化application 注册了广播，发送的时候 用广播发送，广播接收在发送给 各自进程里的 总线

1月20日修改代码，改为AIDL方式传递数据，需要传递的数据继承Serializable
```
open class ChangeUIEvent(var msg:String) :Serializable

val changeUIEvent = ChangeUIEvent("SecondActivity msg")
postEventProcessAIDL(changeUIEvent)

```

其实Flow原理也不是非常复杂，简单阅读了源码 https://juejin.cn/post/7051544459716067365/
