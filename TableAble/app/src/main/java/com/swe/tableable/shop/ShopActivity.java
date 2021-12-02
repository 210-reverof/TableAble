package com.swe.tableable.shop;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.swe.tableable.R;
import com.swe.tableable.Store;
import com.swe.tableable.density.Density;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    private Intent intent;
    private TextView shopName, shopAddress, density;
    private Button btn;
    private Density getDensityData;
    private Store getStoreData;
    private Drawable drawable;
    private ImageView densityImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        init();
    }
    private void init(){
        intent = getIntent();
        getDensityData = (Density) intent.getSerializableExtra("Density");
        getStoreData = (Store)intent.getSerializableExtra("Store");

        shopAddress = findViewById(R.id.shop_address);
        shopName = findViewById(R.id.shop_name);
        density = findViewById(R.id.now_density);
        densityImg = findViewById(R.id.densityImg);

        shopName.setText(getDensityData.getPlaceName());
        shopAddress.setText(getStoreData.getAddress());

        if (getDensityData.getDensity() >= 0 && getDensityData.getDensity() < 20) {
            drawable = getResources().getDrawable(R.drawable.density_img_0);
            density.setText("여유");
            densityImg.setImageDrawable(drawable);
        } else if (getDensityData.getDensity() >= 20 && getDensityData.getDensity() < 40) {
            drawable = getResources().getDrawable(R.drawable.density_img_1);
            density.setText("여유");
            densityImg.setImageDrawable(drawable);
        } else if ((getDensityData.getDensity() >= 40 && getDensityData.getDensity() < 60)) {
            drawable = getResources().getDrawable(R.drawable.density_img_2);
            density.setText("보통");
            densityImg.setImageDrawable(drawable);
        } else if (getDensityData.getDensity() >= 60 && getDensityData.getDensity() < 80) {
            drawable = getResources().getDrawable(R.drawable.density_img_3);
            density.setText("혼잡");
            densityImg.setImageDrawable(drawable);
        } else {
            drawable = getResources().getDrawable(R.drawable.density_img_4);
            density.setText("혼잡");
            densityImg.setImageDrawable(drawable);
        }

        btn = findViewById(R.id.graphbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShopGraphActivity.class);
                intent.putExtra("Density",getDensityData);
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
                    //1은 전체 데이터
                    //2는 복잡도 데이터
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

        return data[0];
    }

    // <Store> 배열로 반환하는 함수
    public ArrayList<Store> toStoreArray() {
        String data = getData();        // 웹상에서 데이터 가져오기
        Log.v("ttttt", data);
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
