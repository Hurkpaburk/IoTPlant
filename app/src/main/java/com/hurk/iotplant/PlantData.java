package com.hurk.iotplant;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlantData implements Serializable {

    private String plantName;

    private ArrayList<DataItem> arrayListData = new ArrayList<DataItem>();

    public PlantData(String name) {
        plantName = name;
    }

    public void addData(DataItem Data) {
        arrayListData.add(Data);
    }

    public void setName(String name) {
        plantName = name;
    }

    public ArrayList<DataItem> getData() {
        return arrayListData;
    }

    @Override
    public String toString() {
        return plantName;
    }
}

