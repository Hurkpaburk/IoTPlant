package com.hurk.iotplant;

public class Data {

    private int time;
    private int data;

    public Data() {

    }

    public Data(int inTime, int inData) {
        this.time = inTime;
        this.data = inData;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getData() {
        return this.data;
    }
}