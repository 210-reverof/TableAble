package com.swe.tableable.map;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.swe.tableable.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;



//디비 -> 가게정보
//자표 -> 찍을꺼야 -> 디비에서 좌표값을 가져와 ->
//누르면 가게이름이랑 복잡도
//가게이름이랑 복잡도

//실시간 -> 버튼을 눌러서 새로고침


public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener {

    MapView mapView;
    RelativeLayout mapViewContainer;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_map);
        mapViewContainer = findViewById(R.id.map);
        mapView = new MapView(this);

        mapView.setCurrentLocationEventListener(this);
        mapViewContainer.addView(mapView);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
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
}
