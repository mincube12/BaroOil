package com.example.barooil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiconnect apicon = new apiconnect();
        apicon.start();

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