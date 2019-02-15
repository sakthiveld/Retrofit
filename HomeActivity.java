package com.example.sakthivel.retrofitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button piechart = findViewById(R.id.piechart);
        piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PieChartActivity.class));
                //Toast.makeText(HomeActivity.this, "Processing !!!", Toast.LENGTH_SHORT).show();
            }
        });

        Button barchart = findViewById(R.id.barchart);
        barchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BarChartActivity.class));
            }
        });
    }
}
