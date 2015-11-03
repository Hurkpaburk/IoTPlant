package com.hurk.iotplant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FullscreenActivity extends Activity {

    private TextView dataText;
    private PlantData plant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        dataText = (TextView) findViewById(R.id.dataText);
        plant = (PlantData) getIntent().getSerializableExtra("plant");

        DataItem selected = plant.getData().get(plant.getData().size() - 1); //.toString();
        dataText.setText("Last Entry: " + selected.toString() + "\nTemp: " + selected.getTemp() + "\nMoisture: " + selected.getMoisture() + "\nuBatt: " + selected.getuBatt());

        final ListView dataList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<DataItem> arrayAdapter = new ArrayAdapter<DataItem>(this, android.R.layout.simple_list_item_1, plant.getData());
        dataList.setAdapter(arrayAdapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                DataItem selected = plant.getData().get(position); //.toString();
                dataText.setText(selected.toString() + "\nTemp: " + selected.getTemp() + "\nMoisture: " + selected.getMoisture() + "\nuBatt: " + selected.getuBatt());
            }
        });
    }
}