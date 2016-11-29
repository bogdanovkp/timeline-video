package com.example.bogdanovkp.testscale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by bogdanovkp on 01.09.2016.
 */
public class MyScaleActivity extends AppCompatActivity {
    private Button button, button1;
    MyScaleView rulerViewMm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scale);
        rulerViewMm = (MyScaleView) findViewById(R.id.my_scale);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulerViewMm.addScale();
            }
        });

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulerViewMm.deductScale();
            }
        });

    }
}
