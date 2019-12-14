package com.kkoudelka.tallycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String COUNT = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        final TextView currentCountText = findViewById(R.id.count_txt);
        currentCountText.setText(String.valueOf(sharedPreferences.getInt(COUNT, 0)));

        final Button incrementBtn = findViewById(R.id.btn_add);
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = sharedPreferences.getInt(COUNT, 0);
                c++;
                currentCountText.setText(String.valueOf(c));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(COUNT, c);
                editor.commit();
            }
        });

        final Button resetBtn = findViewById(R.id.btn_reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(COUNT, 0);
                editor.commit();
                currentCountText.setText("0");
            }
        });
    }
}
