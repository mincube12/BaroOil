package com.example.barooil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity
        implements OnMapReadyCallback {
    ArrayList<point> oilpoint = new ArrayList<>();
    point mypoint = new point();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mypoint = return_my_location();
        GeoPoint geoPoint = new GeoPoint(mypoint.lon , mypoint.lat);
        GeoPoint ffff = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, geoPoint);
        GeoPoint fff1 = GeoTrans.convert(GeoTrans.TM, GeoTrans.KATEC, ffff);
        Log.d("current_location", "현재 위치 찾기 시작   x : " +fff1.x+ "   y: " +fff1.y);

        double x= fff1.x;          // 멤버 기본값 설정
        double y= fff1.y;
        int radius=1500;
        int sort=2;
        String prodcd=("B027");

        apiconnect mapcon = new apiconnect();
        mapcon.setmember(x,y,radius,sort, prodcd);
        mapcon.start();
        try {
            mapcon.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        oil_lat_lon();


        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady(final GoogleMap googleMap) { //이부분을 API 활용 해야할듯.

            mMap = googleMap;

            LatLng me = new LatLng(mypoint.lat, mypoint.lon);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(me);
            markerOptions.title("나의 위치");
            markerOptions.snippet("세명대학교");
            mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            for (int k=0; k<jsonpaser.oil_length; k++){
                LatLng marker = new LatLng(oilpoint.get(k).lat, oilpoint.get(k).lon);
                MarkerOptions markerOptionss = new MarkerOptions();
                markerOptionss.position(marker);
                markerOptionss.title(jsonpaser.nm.get(k));
                markerOptionss.snippet("휘발유 1L : "+ jsonpaser.price.get(k)+"원");
                mMap.addMarker(markerOptionss);
            }
        }




        public void oil_lat_lon(){
        oilpoint.clear();
        for (int i =0; i<jsonpaser.oil_length; i++){
            Geocoder geocoder = new Geocoder(this);
            List<Address> list = null;
            try {
                list = geocoder.getFromLocationName(jsonpaser.address.get(i), 10);
            }catch (IOException e){
            }
            Address address = list.get(0);
            double lat = address.getLatitude();
            double log = address.getLongitude();
            point oil_point = new point();
            oil_point.lat =lat;
            oil_point.lon =log;
            oilpoint.add(oil_point);
        }
    }



    public point return_my_location()
    {
        final Handler my_location_handler = new Handler();
        Log.d("current_location", "test2");
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        point mylocation = new point();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Log.d("current_location", "현재 위치 찾기 시작");
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location == null) {
                //gps를 이용한 좌표조회 실패시 network로 위치 조회
                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location != null) {
                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
                Log.d("current_location", "현재 위치 찾기 시작" +latitude+ "");
                mylocation.lat = latitude;
                mylocation.lon = longitude;
            }
        }



        return mylocation;
    }

    public void setmarker(){
        for (int k=0; k<jsonpaser.oil_length; k++){
            LatLng marker = new LatLng(oilpoint.get(k).lat, oilpoint.get(k).lon);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(marker);
            markerOptions.title(jsonpaser.nm.get(k));
            markerOptions.snippet("휘발유 1L : "+ jsonpaser.price.get(k)+"원");
            mMap.addMarker(markerOptions);
        }
    }

    }
