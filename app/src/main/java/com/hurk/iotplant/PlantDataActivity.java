package com.hurk.iotplant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlantDataActivity extends Activity {

    private TextView dataText;
    private PlantData plant;
    private ArrayList<DataItem> dataItemArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        dataText = (TextView) findViewById(R.id.dataText);
        plant = (PlantData) getIntent().getSerializableExtra("plant");
        dataItemArrayList = plant.getData();
        final ListView dataList = (ListView) findViewById(R.id.listView);
        DataItem selected = dataItemArrayList.get(0); // DataItem selected = dataItemArrayList.get(dataItemArrayList.size() - 1);

        dataText.setText("Last Entry:\n" + getString(R.string.data_text, selected.getTime(), selected.getTemp(), selected.getMoisture(), selected.getuBatt()));

        //Not needed to reverse if JSON read starts from end. Collections.reverse(dataItemArrayList); // Reverse list
        ArrayAdapter<DataItem> arrayAdapter = new ArrayAdapter<DataItem>(this, android.R.layout.simple_list_item_1, dataItemArrayList);
        dataList.setAdapter(arrayAdapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                DataItem selected = dataItemArrayList.get(position);
                dataText.setText(getString(R.string.data_text, selected.getTime(), selected.getTemp(), selected.getMoisture(), selected.getuBatt()));
            }
        });
    }
}