package com.swe.tableable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swe.tableable.afterdensity.AfterDensity;
import com.swe.tableable.afterdensity.AfterDensityActivity;
import com.swe.tableable.density.DensityActivity;
import com.swe.tableable.prseat.PrSeatActivity;
import com.swe.tableable.shop.ShopActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private Button b1, b2, b3, b4;
    private TextView logTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     // 메뉴 드로어 레이아웃 추가
        drawerView = (View) findViewById(R.id.drawer);      // 드로어를 추가

        logTv = findViewById(R.id.tv1);     //로그를 위한 임시 텍스트뷰 (삭제 요망)
        //logTv.setText(getData());
        toJSONArray();  // 메서드 내부적으로 예외 처리 예쩡


        b1 = findViewById(R.id.temp_btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DensityActivity.class);
                startActivity(intent);
            }
        });

        b2 = findViewById(R.id.temp_btn2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(intent);
            }
        });

        b3 = findViewById(R.id.temp_btn3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrSeatActivity.class);
                startActivity(intent);
            }
        });

        b4 = findViewById(R.id.temp_btn4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AfterDensityActivity.class);
                startActivity(intent);
            }
        });


        Button btn_open = findViewById(R.id.menu_btn);      //왼쪽 상단 메뉴 버튼
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        drawerView.setOnTouchListener(new View.OnTouchListener() {      // 눌렀을 때 메뉴 나오기
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });  // 메뉴바 열기

        Button btn_close = findViewById(R.id.btn_close);        // 메뉴바 닫기
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        Button first_btn = findViewById(R.id.first_btn);            // 특정 메뉴로 이동
        first_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, DensityActivity.class);
                startActivity(intent);
            }
        });

        Button second_btn = findViewById(R.id.second_btn);
        second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, PrSeatActivity.class);
                startActivity(intent);
            }
        });

        Button third_btn = findViewById(R.id.third_btn);
        third_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        Button fourth_btn = findViewById(R.id.fourth_btn);
        fourth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, AfterDensityActivity.class);
                startActivity(intent);
            }
        });
    }

    // php 문서에서 JSON을 String으로 받아오기
    public String getData() {
        final String[] data = new String[1];

        class RequestThread extends Thread {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://220.69.208.171/mariadb/mtest1.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn != null) {
                        conn.setConnectTimeout(10000); // 10초 동안 기다린 후 응답이 없으면 종료
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        int resCode = conn.getResponseCode();
                        if (resCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line = null;
                            while (true) {
                                line = reader.readLine();
                                if (line == null)
                                    break;
                                data[0] += line;
                            }
                            reader.close();
                        }
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        RequestThread thread = new RequestThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.v("rrrrrr",data[0]);
        return data[0];
    }

    // <Store> 배열로 반환하기
    //public ArrayList<Store> toJSONArray () throws JSONException {
    public void toJSONArray() throws JSONException {
        String data = getData();
        data = data.substring(4);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String.valueOf(jsonObject.get("name"));


    }
}