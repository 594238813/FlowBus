package com.flowbuslib.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FlowBusEventAIDL implements Parcelable {

    public int code;
    public String msg;
    public Serializable data;

    public FlowBusEventAIDL(){}


    protected FlowBusEventAIDL(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data =  in.readSerializable();
    }

    public static final Creator<FlowBusEventAIDL> CREATOR = new Creator<FlowBusEventAIDL>() {
        @Override
        public FlowBusEventAIDL createFromParcel(Parcel in) {
            return new FlowBusEventAIDL(in);
        }

        @Override
        public FlowBusEventAIDL[] newArray(int size) {
            return new FlowBusEventAIDL[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeSerializable(data);
    }
}
