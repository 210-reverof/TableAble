package com.swe.tableable.prseat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swe.tableable.R;

import java.util.ArrayList;
import java.util.List;

public class PrSeatActivity  extends AppCompatActivity {
    private List<PrSeat> getList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PrSeatRecyclerAdapter prSeatRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prdt);
        init();
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
        getList.add(new PrSeat("빨봉", "30분", 30));
        getList.add(new PrSeat("버베이드", "10분", 4));
    }
}
