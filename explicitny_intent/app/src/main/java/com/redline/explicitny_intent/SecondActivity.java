package com.redline.explicitny_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtMessage = findViewById(R.id.txt_message);

        Intent intent = getIntent();
        txtMessage.setText(intent.getStringExtra("message"));
    }
}
