package com.flowbuslib

import java.io.Serializable

data class FlowBusEvent<T>(val code:Int,val msg:T): Serializable