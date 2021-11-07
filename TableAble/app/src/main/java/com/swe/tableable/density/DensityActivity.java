package com.swe.tableable.density;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swe.tableable.MainActivity;
import com.swe.tableable.R;
import com.swe.tableable.Store;
import com.swe.tableable.afterdensity.AfterDensityActivity;
import com.swe.tableable.map.MapActivity;
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
import java.util.List;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.incrementExact;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DensityActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private List<Density> getList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DensityRecyclerAdapter densityRecyclerAdapter;
    private ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     // 메뉴 드로어 레이아웃 추가
        drawerView = (View) findViewById(R.id.drawer);      // 드로어를 추가
        
        stores = toStoreArray();   // 받아온 데이터로 배열 만들기

        init();

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
                Intent intent = new Intent(DensityActivity.this,DensityActivity.class);
                startActivity(intent);
            }
        });

        Button second_btn = findViewById(R.id.second_btn);
        second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(DensityActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        Button third_btn = findViewById(R.id.third_btn);
        third_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(DensityActivity.this, PrSeatActivity.class);
                startActivity(intent);
            }
        });

        Button fourth_btn = findViewById(R.id.fourth_btn);
        fourth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(DensityActivity.this, AfterDensityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        insertList();
        recyclerView = findViewById(R.id.density_recycler_view);
        densityRecyclerAdapter = new DensityRecyclerAdapter(getList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(densityRecyclerAdapter);
    }

    private void insertList() {
        double density;
        for (Store i : stores) {
            density = (double)(i.getTable1Sit()+i.getTable2Sit()+i.getTable4Sit()) / (double) (i.getTable1Cnt()+i.getTable2Cnt()+i.getTable4Cnt()) * 100;
            Log.v("here", i.getLongitude()+"");
            getList.add(new Density(i.getStoreNum(),i.getStoreName(), i.getLatitude(), i.getLongitude(), density));
        }

        // getList.add(new Density(1,"신전떡볶이",30.0, 60.0, 40.0));
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

        Log.v("rrrrrr", data[0]);
        return data[0];
    }

    // <Store> 배열로 반환하는 함수
    public ArrayList<Store> toStoreArray() {
        String data = getData();        // 웹상에서 데이터 가져오기
        Log.v("ttttt",data);
        data = data.substring(4);       // 앞 네글자 자르기
        JSONArray jsonArray = null;

        JSONObject jsonObject = null;
        ArrayList<Store> stores = new ArrayList<Store>();

        try {
            jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                stores.add(new Store(
                        Integer.parseInt(String.valueOf(jsonObject.get("STORENUM"))),
                        String.valueOf(jsonObject.get("STORENAME")),
                        Double.parseDouble(String.valueOf(jsonObject.get("LATITUDE"))),
                        Double.parseDouble(String.valueOf(jsonObject.get("LONGITUDE"))),
                        String.valueOf(jsonObject.get("ADDRESS")),
                        Integer.parseInt(String.valueOf(jsonObject.get("TABLE1CNT"))), Integer.parseInt(String.valueOf(jsonObject.get("TABLE1SIT"))),
                        Integer.parseInt(String.valueOf(jsonObject.get("TABLE2CNT"))), Integer.parseInt(String.valueOf(jsonObject.get("TABLE2SIT"))),
                        Integer.parseInt(String.valueOf(jsonObject.get("TABLE4CNT"))), Integer.parseInt(String.valueOf(jsonObject.get("TABLE4SIT"))),
                        Double.parseDouble(String.valueOf(jsonObject.get("SITAVG")))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stores;
    }

}
