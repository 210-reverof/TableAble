package com.swe.tableable.density;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swe.tableable.R;

import java.util.ArrayList;
import java.util.List;

public class DensityActivity extends AppCompatActivity {

    private List<Density> getList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DensityRecyclerAdapter densityRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        init();
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
