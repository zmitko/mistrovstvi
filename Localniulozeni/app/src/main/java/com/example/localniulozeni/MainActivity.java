package com.example.localniulozeni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button add;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        text = findViewById(R.id.text);
        text.setText(String.valueOf(pref.getInt("number", 0)));
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("number", pref.getInt("number", 0)+1);
                editor.commit();
                text.setText(String.valueOf(pref.getInt("number", 0)));
            }
        });
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("number", 0);
                editor.commit();
                text.setText(String.valueOf(pref.getInt("number", 0)));
            }
        });
    }
}
