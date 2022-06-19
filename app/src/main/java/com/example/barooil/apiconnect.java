package com.example.barooil;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class apiconnect extends Thread {
    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL("https://www.opinet.co.kr/api/aroundAll.do?code=F220607173" +
                    "&x=314681.8" +                 //x좌표
                    "&y=544837" +                   //y좌표
                    "&radius=1500" +                //주변 반경
                    "&sort=1" +                     //1: 가격순, 2: 거리순
                    "&prodcd=B027" +                //제품 선택(휘발유:B027, 경유:D047, 고급휘발유: B034, 실내등유: C004, 자동차부탄: K015)
                    "&out=json");
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

            String result = buffer.toString();
            jsonpaser.Jsonpaser(result);
        }
        catch (Exception e) {
        }
    }
}
