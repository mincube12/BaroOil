package com.example.barooil;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class jsonpaser {
    public static ArrayList<String> id;
    public static ArrayList<String> price;
    public static ArrayList<String> distance;
    public static ArrayList<String> x_location;
    public static ArrayList<String> y_location;

    public static void Jsonpaser(String resultjson){
    id = new ArrayList<>();
    price = new ArrayList<>();
    distance = new ArrayList<>();
    x_location = new ArrayList<>();
    y_location = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(resultjson);
            JSONObject result = jsonObject.getJSONObject("RESULT");
            Log.d("parsing","id : "+result);
            JSONArray oil = result.getJSONArray("OIL");
            for (int i=0; 0<oil.length(); i++){
                JSONObject near_oil = oil.getJSONObject(i);
                id.add(near_oil.getString("UNI_ID"));
                Log.d("parsing","id : "+id.get(i));
                price.add(near_oil.getString("PRICE"));
                Log.d("parsing","id : "+price.get(i));
                distance.add(near_oil.getString("DISTANCE"));
                Log.d("parsing","id : "+distance.get(i));
                x_location.add(near_oil.getString("GIS_X_COOR"));
                Log.d("parsing","id : "+x_location.get(i));
                y_location.add(near_oil.getString("GIS_Y_COOR"));
                Log.d("parsing","id : "+y_location.get(i));

            }

        }catch (JSONException e){

        }
    }
}
