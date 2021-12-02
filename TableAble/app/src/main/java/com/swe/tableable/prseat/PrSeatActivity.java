package com.swe.tableable.prseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.swe.tableable.R;
import com.swe.tableable.afterdensity.AfterDensity;
import com.swe.tableable.afterdensity.AfterDensityActivity;
import com.swe.tableable.density.DensityActivity;
import com.swe.tableable.map.MapActivity;
import com.swe.tableable.shop.ShopActivity;

import java.util.ArrayList;
import java.util.List;

public class PrSeatActivity  extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private List<PrSeat> getList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PrSeatRecyclerAdapter prSeatRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prdt);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     // 메뉴 드로어 레이아웃 추가
        drawerView = (View) findViewById(R.id.drawer);      // 드로어를 추가

        init(); // 리스트 뷰 관련 실행

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

        Button first_btn = findViewById(R.id.first_btn);            // 특정 메뉴로 이동ㄴ
        first_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(PrSeatActivity.this,DensityActivity.class);
                startActivity(intent);
            }
        });

        Button second_btn = findViewById(R.id.second_btn);
        second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(PrSeatActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        Button third_btn = findViewById(R.id.third_btn);
        third_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(PrSeatActivity.this, PrSeatActivity.class);
                startActivity(intent);
            }
        });

        Button fourth_btn = findViewById(R.id.fourth_btn);
        fourth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(PrSeatActivity.this, AfterDensityActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton btn_refresh = findViewById(R.id.prseat_reload_btn);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList.clear();
                //stores = toStoreArray();
                init();
            }
        });
    }

    private void init(){
        insertList();
        recyclerView = findViewById(R.id.prdt_recycler_view);
        prSeatRecyclerAdapter = new PrSeatRecyclerAdapter(getList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(prSeatRecyclerAdapter);
    }

    private void insertList(){
        getList.add(new PrSeat("교촌", "10분", 3));
        getList.add(new PrSeat("빨봉", "15분", 30));
        getList.add(new PrSeat("버베이드", "10분", 4));
    }
}
