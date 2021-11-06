package com.swe.tableable.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.swe.tableable.R;
import com.swe.tableable.Store;
import com.swe.tableable.shop.ShopActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


//디비 -> 가게정보
//자표 -> 찍을꺼야 -> 디비에서 좌표값을 가져와 ->
//누르면 가게이름이랑 복잡도
//가게이름이랑 복잡도

//실시간 -> 버튼을 눌러서 새로고침


public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    MapView mapView;
    RelativeLayout mapViewContainer;
    private ArrayList<Store> stores;


    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_map);
        mapViewContainer = findViewById(R.id.map);
        mapView = new MapView(this);

        stores = toStoreArray();
        makeMarker(stores);

        mapView.setCurrentLocationEventListener(this);
        mapViewContainer.addView(mapView);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

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

                        Log.d("123",String.valueOf(resCode));

                        if (resCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line = null;
                            while (true) {
                                line = reader.readLine();
                                if (line == null)
                                    break;
                                Log.d("test",line);
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

    private void makeMarker(ArrayList<Store>getList){
        MapPOIItem mapPOIItem = new MapPOIItem();
        for(Store dataList : getList){
            mapPOIItem.setItemName(dataList.getStoreName());
            double density = (double)(dataList.getTable1Sit()+dataList.getTable2Sit()+dataList.getTable4Sit()) / (double) (dataList.getTable1Cnt()+dataList.getTable2Cnt()+dataList.getTable4Cnt()) * 100;
            double x = dataList.getLatitude();
            double y = dataList.getLongitude();
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x,y);
            mapPOIItem.setMapPoint(mapPoint);
            mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            if(density <= 25){
                mapPOIItem.setCustomImageResourceId(R.drawable.empty_marker);
            }
            else if(density>25 && density<=75){
                mapPOIItem.setCustomImageResourceId(R.drawable.normal_marker);
            }
            else {
                mapPOIItem.setCustomImageResourceId(R.drawable.busy_marker);
            }
            mapPOIItem.setCustomImageAutoscale(false);
            mapView.addPOIItem(mapPOIItem);
        }
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //마커 클릭시 실행
        //스토어 번호를 넘김
        Intent intent = new Intent(this, ShopActivity.class);
        for(int i = 0; i<stores.size(); i++){
            if(mapPOIItem.getItemName().equals(stores.get(i).getStoreName())){
                intent.putExtra("num",stores.get(i).getStoreNum());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
