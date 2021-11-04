package com.swe.tableable.density;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swe.tableable.MainActivity;
import com.swe.tableable.R;
import com.swe.tableable.afterdensity.AfterDensityActivity;
import com.swe.tableable.prseat.PrSeatActivity;
import com.swe.tableable.shop.ShopActivity;

import java.util.ArrayList;
import java.util.List;

public class DensityActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private List<Density> getList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DensityRecyclerAdapter densityRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);     // 메뉴 드로어 레이아웃 추가
        drawerView = (View) findViewById(R.id.drawer);      // 드로어를 추가

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

        Button first_btn = findViewById(R.id.first_btn);            // 특정 메뉴로 이동ㄴ
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
                Intent intent = new Intent(DensityActivity.this, PrSeatActivity.class);
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

    private void insertList(){
        getList.add(new Density("신전떡볶이",30.0, 60.0, 40));
        getList.add(new Density("엽떡!",30.0, 100.0,30));
        getList.add(new Density("빨봉!",30.0, 30.0,70));
        getList.add(new Density("꼴두바우",30.0, 0.0, 2));
        getList.add(new Density("궁중화로",30.0, 0.0, 34));
        getList.add(new Density("진룽 마라탕",30.0, 0.0, 56));
    }
}
