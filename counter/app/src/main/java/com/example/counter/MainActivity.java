package com.example.counter;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    TextView number;
    Button add, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();

        number = findViewById(R.id.text);
        add = findViewById(R.id.add);
        reset = findViewById(R.id.reset);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(Integer.parseInt(number.getText().toString())+1);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.setText("0");
            }
        });
    }
}
