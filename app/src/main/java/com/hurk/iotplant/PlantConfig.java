package com.hurk.iotplant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PlantConfig extends AppCompatActivity {

    private final String STARTSTRING = "http://api.thingspeak.com/channels/";
    private final String MIDDLESTRING = "/feeds.json?key=";
    private final String ENDSTRING = "&results=8000";
    EditText channel;
    EditText key;
    private String newPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_config);

        channel = (EditText) findViewById(R.id.plantChannel);
        key = (EditText) findViewById(R.id.plantKey);

    }

    public void newPlant(View view) {
        if (!(channel.getText().toString().trim().length() == 0) || !(key.getText().toString().trim().length() == 0)) {
            StringBuilder plantAppend = new StringBuilder();
            plantAppend.append(STARTSTRING);
            plantAppend.append(channel.getText().toString());
            plantAppend.append(MIDDLESTRING);
            plantAppend.append(key.getText().toString());
            plantAppend.append(ENDSTRING);
            newPlant = plantAppend.toString();
            Toast.makeText(getApplicationContext(), "Added:\n" + newPlant, Toast.LENGTH_LONG).show();
            key.getText().clear();
            channel.getText().clear();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("message", newPlant);
        setResult(Activity.RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}
