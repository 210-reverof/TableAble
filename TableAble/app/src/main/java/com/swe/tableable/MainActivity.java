package com.swe.tableable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swe.tableable.density.DensityActivity;
import com.swe.tableable.prseat.PrSeatActivity;
import com.swe.tableable.shop.ShopActivity;

public class MainActivity extends AppCompatActivity {

    private Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.temp_btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DensityActivity.class);
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
    }
}