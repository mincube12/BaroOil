package com.example.barooil;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class jsonpaser {
    public static ArrayList<String> id;
    public static ArrayList<String> nm;
    public static ArrayList<Integer> price;
    public static ArrayList<Integer> distance;
    public static ArrayList<String> x_location;
    public static ArrayList<String> y_location;
    public static int oil_length;

    public static void Jsonpaser(String resultjson){

    id = new ArrayList<>();             //api id 데이터
    nm = new ArrayList<>();             //api 상호 데이터
    price = new ArrayList<>();          //api 가격 데이터
    distance = new ArrayList<>();       //api 거리 데이터
    x_location = new ArrayList<>();     //api x좌표 데이터
    y_location = new ArrayList<>();     //api y좌표 데이터

        try {
            JSONObject jsonObject = new JSONObject(resultjson);
            JSONObject result = jsonObject.getJSONObject("RESULT");
            JSONArray oil = result.getJSONArray("OIL");
            oil_length = oil.length();
            for (int i=0; 0<oil.length(); i++){
                JSONObject near_oil = oil.getJSONObject(i);
                id.add(near_oil.getString("UNI_ID"));
                nm.add(near_oil.getString("OS_NM"));
                price.add(near_oil.getInt("PRICE"));
                distance.add(near_oil.getInt("DISTANCE"));
                x_location.add(near_oil.getString("GIS_X_COOR")+" ["+i+"]");
                y_location.add(near_oil.getString("GIS_Y_COOR")+" ["+i+"]");
                //Log.d("parsing","y : "+y_location.get(i));
                if(i== oil.length())
                    break;

            }

        }catch (JSONException e){   // json 이 끝낫을 경우
            for(int j=0; j<oil_length; j++){        // json 을 거리순으로 정렬
            for(int k=j+1; k<oil_length ; k++){
                if(distance.get(j)>distance.get(k))
                {
                    String t_id = id.get(k);
                    String t_nm = nm.get(k);
                    int t_price = price.get(k);
                    int t_distance = distance.get(k);
                    String t_x = x_location.get(k);
                    String t_y = y_location.get(k);

                    id.set(k,id.get(j));
                    nm.set(k,nm.get(j));
                    price.set(k,price.get(j));
                    distance.set(k,distance.get(j));
                    x_location.set(k,x_location.get(j));
                    y_location.set(k,y_location.get(j));

                    id.set(j,t_id);
                    nm.set(k,t_nm);
                    price.set(j,t_price);
                    distance.set(j,t_distance);
                    x_location.set(j,t_x);
                    y_location.set(j,t_y);
                }
            }
        }
        for (int ii=0; ii<oil_length ; ii++){
            Log.d("parsing","distance : "+distance.get(ii));
        }
        }

    }
}
