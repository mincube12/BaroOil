package com.example.barooil;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class jsonpaser {
    public static ArrayList<String> id;
    public static ArrayList<String> nm;
    public static ArrayList<String> address;
    public static ArrayList<Integer> price;
    public static ArrayList<Integer> distance;
    public static ArrayList<Double> x_location;
    public static ArrayList<Double> y_location;
    public static int oil_length;
    static int sort = 0;

    public static void Jsonpaser(String resultjson, int sor){
    sort = sor;
    id = new ArrayList<>();             //api id 데이터
    nm = new ArrayList<>();             //api 상호 데이터
         address = new ArrayList<>();   //도로명 주소 데이터
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
                address.add(addresspaser(id.get(i)));
                nm.add(near_oil.getString("OS_NM"));
                //Log.d("test","add : "+address.get(i));
                price.add(near_oil.getInt("PRICE"));
                //Log.d("parsing","y : "+price.get(i));
                distance.add(near_oil.getInt("DISTANCE"));
                x_location.add(near_oil.getDouble("GIS_X_COOR"));
                y_location.add(near_oil.getDouble("GIS_Y_COOR"));
                //Log.d("parsing","y : "+y_location.get(i));
                if(i== oil.length())
                    break;

            }

        }catch (JSONException e){   // json 이 끝낫을 경우
            for(int j=0; j<oil_length; j++){        // json 을 거리순으로 정렬
            for(int k=j+1; k<oil_length ; k++){
                if (sort == 1){
                if(distance.get(j)>distance.get(k))
                {
                    String t_id = id.get(k);
                    String t_addr = address.get(k);
                    String t_nm = nm.get(k);
                    int t_price = price.get(k);
                    int t_distance = distance.get(k);
                    double t_x = x_location.get(k);
                    double t_y = y_location.get(k);

                    id.set(k,id.get(j));
                    address.set(k,address.get(j));
                    nm.set(k,nm.get(j));
                    price.set(k,price.get(j));
                    distance.set(k,distance.get(j));
                    x_location.set(k,x_location.get(j));
                    y_location.set(k,y_location.get(j));

                    id.set(j,t_id);
                    address.set(j,t_addr);
                    nm.set(k,t_nm);
                    price.set(j,t_price);
                    distance.set(j,t_distance);
                    x_location.set(j,t_x);
                    y_location.set(j,t_y);
                }
                }
                else if (price.get(j)>price.get(k)){
                    String t_id = id.get(k);
                    String t_addr = address.get(k);
                    String t_nm = nm.get(k);
                    int t_price = price.get(k);
                    int t_distance = distance.get(k);
                    double t_x = x_location.get(k);
                    double t_y = y_location.get(k);

                    id.set(k,id.get(j));
                    address.set(k,address.get(j));
                    nm.set(k,nm.get(j));
                    price.set(k,price.get(j));
                    distance.set(k,distance.get(j));
                    x_location.set(k,x_location.get(j));
                    y_location.set(k,y_location.get(j));

                    id.set(j,t_id);
                    address.set(j,t_addr);
                    nm.set(k,t_nm);
                    price.set(j,t_price);
                    distance.set(j,t_distance);
                    x_location.set(j,t_x);
                    y_location.set(j,t_y);
                }
            }
        }
        for (int ii=0; ii<oil_length ; ii++){
            Log.d("parsing","price : "+price.get(ii));
        }
        }

    }

    public static String addresspaser(String id){
        URL url = null;
        String addr = new String();
        try {
            url = new URL("http://www.opinet.co.kr/api/detailById.do?code=F220607173&" +
                    "id=" + id +
                    "&out=json" );
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (true) {
                buffer.append(line + "\n");
                line = reader.readLine();
                if (line == null) {
                    break;
                }
            }
            String json = buffer.toString();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject result = jsonObject.getJSONObject("RESULT");
                JSONArray oil = result.getJSONArray("OIL");
                JSONObject oil_address = oil.getJSONObject(0);
                addr =  oil_address.getString("VAN_ADR");
            }catch (JSONException e){
            }
    } catch (Exception e) {
        }
        return addr;
    }
}
