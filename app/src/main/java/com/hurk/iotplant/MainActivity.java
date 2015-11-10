package com.hurk.iotplant;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "IoT";
    private final String URLLISA = "http://api.thingspeak.com/channels/62925/feeds.json?key=WM7XSLBEZIO38N9H&results=8000";
    private final String URLSARA = "http://api.thingspeak.com/channels/64990/feeds.json?key=9TJ90ZBZZRLO2OWP&results=8000";
    private String[] plantURL = new String[2];
    private ArrayList<PlantData> plantList = new ArrayList<PlantData>();
    private ListView dataList;
    private ArrayAdapter<PlantData> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plantURL[0] = URLLISA;
        plantURL[1] = URLSARA;

        // BUILD List
        dataList = (ListView) findViewById(R.id.plantList);
        arrayAdapter = new ArrayAdapter<PlantData>(this, android.R.layout.simple_list_item_1, plantList);
        dataList.setAdapter(arrayAdapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                PlantData selected = plantList.get(position); //.toString();
                plantActivity(selected);
            }
        });

        Log.d(DEBUG_TAG, URLLISA);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            for (int i = 0; i < plantURL.length; i++) {
                parseJSON(plantURL[i]);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataList = (ListView) findViewById(R.id.plantList);
        dataList.invalidateViews();
    }

    public void plantActivity(PlantData selected) {
        Intent intent = new Intent(this, PlantDataActivity.class);
        intent.putExtra("plant", selected);
        startActivity(intent);
    }

    private void parseJSON(String stringUrl) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, stringUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("OnResponse", response.toString());

                try {
                    int temp, moisture, uBatt;

                    JSONObject channelInfo = response.getJSONObject("channel");
                    JSONArray arrayData = response.getJSONArray("feeds");

                    String tmpName = channelInfo.getString("name");
                    plantList.add(new PlantData(tmpName));
                    Log.d("JsonObj", tmpName);

                    for (int j = arrayData.length() - 1; j >= 0; j = j - 5) { // only take 5th value start from end
                        JSONObject tmpObj = (JSONObject) arrayData.get(j);
                        Log.d("JsonObj", tmpObj.toString());
                        if (tmpObj.getString("created_at") == "null" || tmpObj.getString("entry_id") == "null") {
                            // do nothing
                        } else {
                            if (!isInteger(tmpObj.getString("field1"))) {
                                temp = -1;
                            } else {
                                temp = tmpObj.getInt("field1");
                            }
                            if (!isInteger(tmpObj.getString("field2"))) {
                                moisture = -1;
                            } else {
                                moisture = tmpObj.getInt("field2");
                            }
                            if (!isInteger(tmpObj.getString("field3"))) {
                                uBatt = -1;
                            } else {
                                uBatt = tmpObj.getInt("field3");
                            }

                            plantList.get(plantList.size() - 1).addData(
                                    new DataItem(tmpObj.getString("created_at"), tmpObj.getString("entry_id"), temp, moisture, uBatt));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("LOGMESS", "ERROR: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
