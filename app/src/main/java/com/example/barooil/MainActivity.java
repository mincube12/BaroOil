package com.example.barooil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;

/*
url 불러오는 클래스 apiconnect
setmember 메소드로 좌표,거리,제품 지정
json 파싱 및 정렬 클래스 jsonpaser
api 데이터는 jsonpaser 클래스의 각 Arraylist 변수들에 저장된다 (id,ditance,price,x_location,y_location)

+ api 가 받는 데이터와 주는 데이터는 좌표 값이고 구글맵은 경도와 위도 이므로 변환할 필요가 있음

좌표 변환법
        GeoPoint a = new GeoPoint(x좌표,y좌표)
        GeoPoint b = GeoTrans.convert(GeoTrans.KATEC,GeoTrans.GEO,a)
    a,b 는 원하는 이름
    위경도 -> 카텍 좌표는 KATEC -> GEO, GEO -> KATEC 으로 변경
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiconnect apicon = new apiconnect();
        apicon.start();// api 데이터 취득 시작
        Button mapbutton = (Button) findViewById(R.id.changemap);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        });

        Button favoritebutton = (Button) findViewById(R.id.changefavorite);
        favoritebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1) {
                Intent intent1 = new Intent(getApplicationContext(), Favorite.class);
                startActivity(intent1);
            }
        });
    }

}