package com.example.mistrovstvi_cv4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StrankaSeJmenemActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranka_se_jmenem);
        textView = findViewById(R.id.txt_jmeno);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("jmeno"));
    }
}
