// IFlowBusManager.aidl
package com.flowbuslib;
import com.flowbuslib.FlowBusEventAIDL;
import com.flowbuslib.IFlowBusCallback;
import android.os.Message;

interface IFlowBusManager {

    void register(IFlowBusCallback callback);
    void unregister(IFlowBusCallback callback);
    void postToService(in FlowBusEventAIDL event);
}