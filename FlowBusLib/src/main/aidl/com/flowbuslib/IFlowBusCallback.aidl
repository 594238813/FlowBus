// IFlowBusCallback.aidl
package com.flowbuslib;
import android.os.Message;
import com.flowbuslib.FlowBusEventAIDL;


interface IFlowBusCallback {

     void onServiceBack(in FlowBusEventAIDL info);

}