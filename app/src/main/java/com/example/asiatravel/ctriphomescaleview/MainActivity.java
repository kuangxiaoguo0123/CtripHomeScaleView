package com.example.asiatravel.ctriphomescaleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.asiatravel.ctriphomescaleview.view.CTripHomeScaleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CTripHomeScaleView cTripHomeScaleView = (CTripHomeScaleView) findViewById(R.id.ctrip_imageView);
        cTripHomeScaleView.setOnClickListener(new CTripHomeScaleView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击事件触发", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
