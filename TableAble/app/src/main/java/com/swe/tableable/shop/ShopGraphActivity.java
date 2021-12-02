package com.swe.tableable.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.swe.tableable.R;
import com.swe.tableable.Store;
import com.swe.tableable.density.Density;
import com.swe.tableable.density.TimeDensity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShopGraphActivity extends AppCompatActivity {

    private LineChart lineChart;
    private Intent intent;
    private Density getDensity;
    private Button backBtn;
    private TextView title;
    private ArrayList<Entry> inputValues = new ArrayList<>();
    private ArrayList<TimeDensity> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_graph);
        init();
        makeGraph();
    }

    private final static Comparator<TimeDensity>sortByTotalCall = new Comparator<TimeDensity>() {
        @Override
        public int compare(TimeDensity o1, TimeDensity o2) {
            return Integer.compare(o1.getHour(), o2.getHour());
        }
    };

    private void init() {
        intent = getIntent();
        getDensity = (Density) intent.getSerializableExtra("Density");

        /* 0시가 빠졌음! */
        list = toDensityArray();
        Collections.sort(list,sortByTotalCall);

        lineChart = findViewById(R.id.line_chart);
        title = findViewById(R.id.graph_place_name);
        backBtn = findViewById(R.id.graph_back_btn);

        title.setText(getDensity.getPlaceName());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //화면 되돌아가기

            }
        });
    }

    private void makeGraph() {
        //float 형태의 데이터를 가짐, 앞에는 x값.
        for (int i = 0; i < list.size(); i++) {
            float val = (float) list.get(i).getDensity();
            inputValues.add(new Entry(list.get(i).getHour(), val));
        }

        //데이터셋 초기화
        LineDataSet set1;
        //앞에서 생성한 values 를 사용하여 이름 지정
        set1 = new LineDataSet(inputValues, getDensity.getPlaceName());

        //데이터셋 추가
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        //완성한 데이터 셋으로 그래프 데이터 생성
        LineData data = new LineData(dataSets);

        //그래프 속성
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        lineChart.setBackgroundColor(Color.parseColor("#fffaea"));
        lineChart.setBorderWidth((float)1.5);
        lineChart.setBorderColor(Color.BLACK);
        lineChart.setDrawBorders(true);
        set1.setDrawFilled(true);
        set1.setFillColor(Color.BLACK);

        //그래프 설정
        lineChart.setData(data);
    }

    public String getData() {
        final String[] data = new String[1];

        class RequestThread extends Thread {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://220.69.208.171/mariadb/mtest2.php");
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
    public ArrayList<TimeDensity> toDensityArray() {
        String data = getData();        // 웹상에서 데이터 가져오기
        data = data.substring(4);       // 앞 네글자 자르기
        JSONArray jsonArray = null;

        JSONObject jsonObject = null;
        ArrayList<TimeDensity> density = new ArrayList<TimeDensity>();

        try {
            jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                density.add(
                        new TimeDensity(Integer.parseInt(String.valueOf(jsonObject.get("STORENUM"))),
                                Integer.parseInt(String.valueOf(jsonObject.get("TIME"))),
                                Double.parseDouble(String.valueOf(jsonObject.get("DENSITY")))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return density;
    }
}
