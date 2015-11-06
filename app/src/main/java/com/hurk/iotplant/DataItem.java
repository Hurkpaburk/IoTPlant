package com.hurk.iotplant;

import java.io.Serializable;

/**
 * Created by Hurk on 2015-11-02.
 */
public class DataItem implements Serializable {

    private String timeStamp = null;
    private String id = null;
    private int moisture = 0;
    private int temp = 0;
    private int uBatt = 0;

    // Constructor
    public DataItem() {

    }

    public DataItem(String timesStamp, String id, int moisture, int temp, int uBatt) {

        this.id = id;
        this.timeStamp = timesStamp;
        this.moisture = moisture;
        this.temp = temp;
        this.uBatt = uBatt;
    }

    public String getTime() {
        return timeStamp;
    }

    public String getId() {
        return id;
    }

    public int getMoisture() {
        return moisture;
    }

    public int getTemp() {
        return temp;
    }

    public int getuBatt() {
        return uBatt;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time: ");
        stringBuilder.append(getTime());
        stringBuilder.append("\nTemp: ");
        stringBuilder.append(getTemp());
        stringBuilder.append(", Moisture: ");
        stringBuilder.append(getMoisture());
        stringBuilder.append(", uBatt: ");
        stringBuilder.append(getuBatt());
        return stringBuilder.toString();
    }
}

